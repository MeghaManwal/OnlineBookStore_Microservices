package com.microservices.bookservice.utils;


import java.util.UUID;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Token {

	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	public final String TOKEN_SECRET = "sd5745FAHFW";
	
	public UUID decodeJWT(String jwt) throws JwtException {
        try {
             Claims claims = Jwts.parser()
                             .setSigningKey("sd5745FAHFW")
                             .parseClaimsJws(jwt)
                             .getBody();
             System.out.println("jwt id: " + claims.getId());
             return UUID.fromString(claims.getId());
         } catch (ExpiredJwtException e) {
             throw new JwtException("session time out");
         }
     
    }

}
