package com.example.authservice.service;

import com.example.authservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

@Service
public class JwtCreator {
    private final PrivateKey privateKey;
    @SneakyThrows
    public JwtCreator(@Value("${msa.auth.private}") String privateKey) {
        String formattedKey = privateKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        byte[] keyBytes = Base64.getDecoder().decode(formattedKey); // Use java.util.Base64 here
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey = keyFactory.generatePrivate(keySpec);
    }

    public String createJwt(User user) {
        return Jwts.builder()
                .setSubject(user.getName())
                .addClaims(Map.of("player", user.isPlayer()))
                .signWith(privateKey)
                .compact();
    }

    public boolean validateJwt(String user, String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(privateKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String tokenUser = claims.getSubject();
            return user.equals(tokenUser);
        } catch (Exception e) {
            return false;
        }
    }
}

