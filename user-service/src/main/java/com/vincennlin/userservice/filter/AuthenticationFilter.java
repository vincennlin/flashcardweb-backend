package com.vincennlin.userservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.userservice.payload.LoginDto;
import com.vincennlin.userservice.payload.UserDto;
import com.vincennlin.userservice.security.FlashcardwebUserDetails;
import com.vincennlin.userservice.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment environment;

    public AuthenticationFilter(UserService userService,
                                Environment environment,
                                AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.userService = userService;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String username = ((FlashcardwebUserDetails) authResult.getPrincipal()).getUsername();
        UserDto userDto = userService.getUserDetailsByUsername(username);

        String tokenSecret = environment.getProperty("token.secret");
        String tokenExpirationTime = environment.getProperty("token.expiration_time");

        byte[] tokenSecretBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(tokenSecretBytes);

        Instant now = Instant.now();
        String token = Jwts.builder()
                .claim("scope", authResult.getAuthorities())
                .subject(userDto.getId().toString())
                .expiration(Date.from(now.plusMillis(Long.parseLong(tokenExpirationTime))))
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();

        response.addHeader("token_type", "Bearer");
        response.addHeader("access_token", token);
        response.addHeader("user_id", userDto.getId().toString());
    }
}
