package com.vincennlin.authservice.service;

import com.vincennlin.authservice.payload.LoginDto;
import com.vincennlin.authservice.payload.LoginResponse;
import com.vincennlin.authservice.payload.RegisterDto;
import com.vincennlin.authservice.payload.RegisterResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    RegisterResponse register(RegisterDto registerDto);

    LoginResponse login(LoginDto loginDto);
}
