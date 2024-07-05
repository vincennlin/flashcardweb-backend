package com.vincennlin.flashcardwebbackend.service;


import com.vincennlin.flashcardwebbackend.payload.security.LoginDto;
import com.vincennlin.flashcardwebbackend.payload.security.RegisterDto;
import com.vincennlin.flashcardwebbackend.payload.security.UserDto;

import java.util.List;

public interface AuthService {

    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);

    List<UserDto> getAllUsers();
}
