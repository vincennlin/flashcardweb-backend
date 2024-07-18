package com.vincennlin.userservice.service;

import com.vincennlin.userservice.payload.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    RegisterResponse register(RegisterDto registerDto);

    UserDto getUserDetailsByUsername(String username);

    UserDto getUserByUserId(Long userId);

    AccountInfoDto getCurrentAccountInfo();

    List<AccountInfoDto> getAllUsers();

    UpdateAccountInfoResponse updateAccountInfo(AccountInfoDto accountInfoDto);
}
