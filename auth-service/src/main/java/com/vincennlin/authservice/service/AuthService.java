package com.vincennlin.authservice.service;

import com.vincennlin.authservice.payload.LoginDto;
import com.vincennlin.authservice.payload.LoginResponse;
import com.vincennlin.authservice.payload.RegisterDto;
import com.vincennlin.authservice.payload.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterDto registerDto);

    LoginResponse login(LoginDto loginDto);
}
