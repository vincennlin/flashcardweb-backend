package com.vincennlin.flashcardwebbackend.service;


import com.vincennlin.flashcardwebbackend.payload.LoginDto;
import com.vincennlin.flashcardwebbackend.payload.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);
}
