package com.vincennlin.userservice.service;

import com.vincennlin.userservice.payload.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends UserDetailsService {

    RegisterResponse register(RegisterDto registerDto);

    UserDto getUserDetailsByUsername(String username);

    UserDto getUserByUserId(Long userId);

    AccountInfoDto getCurrentAccountInfo();

    byte[] getCurrentUserProfilePicture();

    byte[] getProfilePictureByUserId(Long userId);

    List<AccountInfoDto> getAllUsers();

    UpdateAccountInfoResponse updateAccountInfo(AccountInfoDto accountInfoDto);

    UpdateAccountInfoResponse changePassword(ChangePasswordRequest request);

    UpdateAccountInfoResponse updateProfilePicture(MultipartFile profilePicture);
}
