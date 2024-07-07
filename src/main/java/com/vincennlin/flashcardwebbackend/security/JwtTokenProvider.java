package com.vincennlin.flashcardwebbackend.security;


import com.vincennlin.flashcardwebbackend.exception.WebAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return Jwts.builder().subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(key())
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String jwt) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseClaimsJws(jwt)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parse(jwt);
            return true;
        } catch (MalformedJwtException malformedJwtException) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "JWT token has expired");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Jwt claims string is empty");
        } catch (SignatureException signatureException) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        }
    }

}
