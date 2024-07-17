package com.vincennlin.authservice.service;

import com.vincennlin.authservice.payload.*;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    RegisterResponse register(RegisterDto registerDto);

    UserDto getUserDetailsByUsername(String username);
}
