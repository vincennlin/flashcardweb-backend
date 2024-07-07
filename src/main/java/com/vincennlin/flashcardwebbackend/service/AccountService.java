package com.vincennlin.flashcardwebbackend.service;

import com.vincennlin.flashcardwebbackend.payload.auth.UserDto;

import java.util.List;

public interface AccountService {

    UserDto getCurrentAccountInfo();

    List<UserDto> getAllUsers();
}