package com.upc.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "tuClaveSecreta"; // Usa una clave segura en producción
    private static final long EXPIRATION_TIME = 3600000; // 1 hora en milisegundos

    public String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
        // Lógica para extraer el nombre de usuario del token
        return token;
    }

    private boolean isTokenExpired(String token) {
        Date expiration = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token)
                .getExpiresAt();
        return expiration.before(new Date());
    }
}
