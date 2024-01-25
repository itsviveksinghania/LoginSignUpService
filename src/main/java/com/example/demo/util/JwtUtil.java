package com.example.demo.util;


import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;



@Component
public class JwtUtil {
    @Value("${jwt.expiration}")
    private  Long expiration;

    private SecretKey Key;

    public JwtUtil() {
        this.Key = generateRandomKey();
    }

    private SecretKey generateRandomKey() {
        byte[] randomKeyBytes = new byte[32];
        new SecureRandom().nextBytes(randomKeyBytes);

        String base64Key = Base64.getEncoder().encodeToString(randomKeyBytes);

        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(Key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Key)
                .compact();
    }
}

