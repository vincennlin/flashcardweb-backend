package com.vincennlin.userservice.service;

import com.vincennlin.userservice.payload.*;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    RegisterResponse register(RegisterDto registerDto);

    UserDto getUserDetailsByUsername(String username);

    UserDto getUserByUserId(Long userId);
}
