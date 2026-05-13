package com.abhinav.smart_library_system.Config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    // In a real project, this secret would be in an environment variable
    private final String jwtSecret = "your-very-secure-and-very-long-secret-key-for-library-system";
    private final int jwtExpirationMs = 86400000; // 24 hours

    private final Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, String username) {
        try {
            // 1. Verify the signature and format
            String tokenUsername = getUsernameFromToken(token);
            // 2. Check if the username in token matches the DB username
            return (tokenUsername.equals(username));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}