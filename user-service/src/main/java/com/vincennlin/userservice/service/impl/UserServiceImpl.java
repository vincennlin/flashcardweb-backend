package com.vincennlin.userservice.service.impl;

import com.vincennlin.userservice.entity.User;
import com.vincennlin.userservice.exception.WebAPIException;
import com.vincennlin.userservice.payload.*;
import com.vincennlin.userservice.repository.UserRepository;
import com.vincennlin.userservice.security.FlashcardwebUserDetails;
import com.vincennlin.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Qualifier("userServiceModelMapper")
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RegisterResponse register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Username is already taken!");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Email is already taken!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        User newUser = userRepository.save(user);
//
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setMessage("User registered successfully!");
        registerResponse.setUserDto(modelMapper.map(newUser, UserDto.class));

        return registerResponse;
    }

    @Override
    public UserDto getUserDetailsByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new WebAPIException(HttpStatus.NOT_FOUND, "User not found with username: " + username));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new WebAPIException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

//        Set<GrantedAuthority> authorities = user
//                .getRoles()
//                .stream()
//                .map((role) -> (GrantedAuthority) role::getName).collect(Collectors.toSet());

        return new FlashcardwebUserDetails(user.getUsername(), user.getEmail(),
                user.getPassword(), new ArrayList<>(), user.getId());
    }
}
