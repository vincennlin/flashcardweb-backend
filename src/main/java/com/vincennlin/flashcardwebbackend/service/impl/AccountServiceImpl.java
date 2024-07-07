package com.vincennlin.flashcardwebbackend.service.impl;

import com.vincennlin.flashcardwebbackend.entity.User;
import com.vincennlin.flashcardwebbackend.exception.UserNotFoundException;
import com.vincennlin.flashcardwebbackend.exception.WebAPIException;
import com.vincennlin.flashcardwebbackend.payload.auth.UserDto;
import com.vincennlin.flashcardwebbackend.repository.RoleRepository;
import com.vincennlin.flashcardwebbackend.repository.UserRepository;
import com.vincennlin.flashcardwebbackend.security.FlashcardwebUserDetails;
import com.vincennlin.flashcardwebbackend.service.AccountService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private ModelMapper modelMapper;

    @Override
    public UserDto getCurrentAccountInfo() {

        Long currentUserId = getCurrentUserId();

        User user = userRepository.findById(currentUserId).orElseThrow(() ->
                new UserNotFoundException(currentUserId));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {

        return userRepository.findAll().stream().map(user ->
                modelMapper.map(user, UserDto.class)).toList();
    }

    private FlashcardwebUserDetails getUserDetails() {
        return (FlashcardwebUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Long getCurrentUserId() {
        return getUserDetails().getUserId();
    }
}
