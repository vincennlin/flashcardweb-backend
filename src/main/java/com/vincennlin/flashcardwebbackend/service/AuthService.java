package com.vincennlin.flashcardwebbackend.service;


import com.vincennlin.flashcardwebbackend.payload.auth.*;

import java.util.List;

public interface AuthService {

    RegisterResponse register(RegisterDto registerDto);

    LoginResponse login(LoginDto loginDto);

    List<UserDto> getAllUsers();
}
