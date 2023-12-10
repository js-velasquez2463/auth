package com.upc.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Check if the username is "juan" and the password is "test"
        if ("user".equals(username) && "test".equals(password)) {
            // If the credentials are correct, create and return an Authentication object
            List<GrantedAuthority> authorities = new ArrayList<>();
            // Here, you can assign roles to the user (if needed)
            // e.g., authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        } else {
            // If the credentials are incorrect, throw a BadCredentialsException
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
