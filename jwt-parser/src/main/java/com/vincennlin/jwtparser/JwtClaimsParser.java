package com.vincennlin.jwtparser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JwtClaimsParser {

    Jwt<?,?> jwtObject;

    public JwtClaimsParser(String jwtString, String secretToken) {
        this.jwtObject = parseJwt(jwtString, secretToken);
    }

    private Jwt<?,?> parseJwt(String jwtString, String secretToken) {
        byte[] tokenSecretBytes = Base64.getEncoder().encode(secretToken.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(tokenSecretBytes);

        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        return jwtParser.parse(jwtString);
    }

    public Collection<? extends GrantedAuthority> getUserAuthorities() {
        Collection<Map<String, String>> scopes  = ((Claims) jwtObject.getPayload()).get("scope", List.class);

        return scopes.stream().map(scopeMap ->
                new SimpleGrantedAuthority(scopeMap.get("authority"))).toList();
    }

    public String getJwtSubject() {
        return  ((Claims) jwtObject.getPayload()).getSubject();
    }

    public Long getUserId() {
        return Long.parseLong(((Claims) jwtObject.getPayload()).get("user_id", String.class));
    }
}
