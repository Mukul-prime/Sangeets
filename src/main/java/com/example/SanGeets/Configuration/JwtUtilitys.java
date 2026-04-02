package com.example.SanGeets.Configuration;

//import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtUtilitys {

    @Value("${jwt.secretKey}")
    private String jwtsecretkey ;



    private SecretKey secretKey(){
        return Keys.hmacShaKeyFor(jwtsecretkey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String role , String email) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);   // role = ROLE_SELLER / ROLE_CUSTOMER


        return Jwts.builder()
                .setClaims(claims)
//                .setSubject(username)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(secretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean isTokenValid(String token, UserDetails user) {
        return extractUsername(token).equals(user.getUsername())
                && !isExpired(token);
    }

    private boolean isExpired(String token) {
        Date exp = Jwts.parserBuilder()
                .setSigningKey(secretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return exp.before(new Date());
    }
}
