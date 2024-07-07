package com.vincennlin.flashcardwebbackend.service;

import com.vincennlin.flashcardwebbackend.payload.auth.*;

public interface AuthService {

    RegisterResponse register(RegisterDto registerDto);

    LoginResponse login(LoginDto loginDto);
}
