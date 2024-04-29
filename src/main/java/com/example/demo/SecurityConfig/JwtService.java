package com.example.demo.SecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {


    public static final String secret = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";



    public String generateToken(int id, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Role", role);
        return createToken(claims, String.valueOf(id), role);
    }

    private String createToken(Map<String, Object> claims, String id, String userName) {

        return Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, getSignKey()).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public Claims verifyToken(String token) throws HttpClientErrorException.Unauthorized {

        return Jwts.parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token)
                .getBody();
    }


}
