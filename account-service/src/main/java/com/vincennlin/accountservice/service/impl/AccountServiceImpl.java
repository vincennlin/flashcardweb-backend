package com.vincennlin.accountservice.service.impl;

import com.vincennlin.accountservice.payload.AccountInfoDto;
import com.vincennlin.accountservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

//    private UserRepository userRepository;
//
//    private ModelMapper modelMapper;
//
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public AccountInfoDto getCurrentAccountInfo() {
//
//        Long currentUserId = getCurrentUserId();
//
//        User user = userRepository.findById(currentUserId).orElseThrow(() ->
//                new UserNotFoundException(currentUserId));
//
//        return modelMapper.map(user, AccountInfoDto.class);
//    }
//
//    @Override
//    public List<AccountInfoDto> getAllUsers() {
//
//        return userRepository.findAll().stream().map(user ->
//                modelMapper.map(user, AccountInfoDto.class)).toList();
//    }
//
//    @Transactional
//    @Override
//    public UpdateAccountInfoResponse updateAccountInfo(AccountInfoDto accountInfoDto) {
//
//        if (userRepository.existsByUsername(accountInfoDto.getUsername())) {
//            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Username is already taken!");
//        }
//
//        if (userRepository.existsByEmail(accountInfoDto.getEmail())) {
//            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Email is already taken!");
//        }
//
//        Long currentUserId = getCurrentUserId();
//
//        User user = userRepository.findById(currentUserId).orElseThrow(() ->
//                new UserNotFoundException(currentUserId));
//
//        if (accountInfoDto.getName() != null)
//            user.setName(accountInfoDto.getName());
//        if (accountInfoDto.getUsername() != null)
//            user.setUsername(accountInfoDto.getUsername());
//        if (accountInfoDto.getEmail() != null)
//            user.setEmail(accountInfoDto.getEmail());
//        if (accountInfoDto.getPassword() != null)
//            user.setPassword(passwordEncoder.encode(accountInfoDto.getPassword()));
//
//        User updatedUser = userRepository.save(user);
//
//        UpdateAccountInfoResponse updateAccountInfoResponse = new UpdateAccountInfoResponse();
//
//        if (accountInfoDto.getUsername() != null || accountInfoDto.getEmail() != null || accountInfoDto.getPassword() != null) {
//            updateAccountInfoResponse.setMessage("User updated successfully! Please login again.");
//        } else {
//            updateAccountInfoResponse.setMessage("User updated successfully!");
//        }
//        updateAccountInfoResponse.setAccountInfo(modelMapper.map(updatedUser, AccountInfoDto.class));
//
//        return updateAccountInfoResponse;
//    }
//
//    private FlashcardwebUserDetails getUserDetails() {
//        return (FlashcardwebUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    }
//
//    private Long getCurrentUserId() {
//        return getUserDetails().getUserId();
//    }
}
