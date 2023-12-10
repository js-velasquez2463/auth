package com.upc.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtTokenFilter(JwtUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = new CustomUserDetailsService();
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String jwtToken = authHeader.substring(7); // Extrae el token sin "Bearer "
                // Aquí implementarías la lógica para validar el token y extraer detalles como el nombre de usuario
                String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    //lógica para establecer la autenticación en el contexto
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        throw new SecurityException("Invalid JWT Token");
                    }
                } else {
                    throw new SecurityException("Invalid User");
                }
            } else {
                throw new SecurityException("JWT Token Required");
            }

            filterChain.doFilter(request, response);
        } catch (SecurityException se) {
            // Maneja la excepción aquí, por ejemplo, enviando un error 401 Unauthorized
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, se.getMessage());
        }
    }
}
