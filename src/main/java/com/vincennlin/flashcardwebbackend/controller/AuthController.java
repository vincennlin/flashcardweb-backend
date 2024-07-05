package com.vincennlin.flashcardwebbackend.controller;

import com.vincennlin.flashcardwebbackend.payload.security.JWTAuthResponse;
import com.vincennlin.flashcardwebbackend.payload.security.LoginDto;
import com.vincennlin.flashcardwebbackend.payload.security.RegisterDto;
import com.vincennlin.flashcardwebbackend.payload.security.UserDto;
import com.vincennlin.flashcardwebbackend.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping(value = {"/register"})
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {

        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = {"/login"})
    public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {

        String jwt = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(jwt);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/manage/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> users = authService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
