package com.vincennlin.authservice.service.impl;


import com.vincennlin.authservice.entity.User;
import com.vincennlin.authservice.exception.WebAPIException;
import com.vincennlin.authservice.payload.*;
import com.vincennlin.authservice.repository.UserRepository;
import com.vincennlin.authservice.security.FlashcardwebUserDetails;
import com.vincennlin.authservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

//    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

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

    //
//    @Override
//    public RegisterResponse register(RegisterDto registerDto) {
//
//        if (userRepository.existsByUsername(registerDto.getUsername())) {
//            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Username is already taken!");
//        }
//
//        if (userRepository.existsByEmail(registerDto.getEmail())) {
//            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Email is already taken!");
//        }
//
//        User user = new User();
//        user.setName(registerDto.getName());
//        user.setUsername(registerDto.getUsername());
//        user.setEmail(registerDto.getEmail());
//        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
//
//        Set<Role> roles = new HashSet<>();
//        if (user.getUsername().equals("admin")) {
//            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
//                    .orElseThrow(() -> new WebAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Admin Role not set."));
//            roles.add(adminRole);
//        } else {
//            Role userRole = roleRepository.findByName("ROLE_USER")
//                    .orElseThrow(() -> new WebAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "User Role not set."));
//            roles.add(userRole);
//        }
//        user.setRoles(roles);
//
//        User newUser = userRepository.save(user);
//
//        RegisterResponse registerResponse = new RegisterResponse();
//        registerResponse.setMessage("User registered successfully!");
//        registerResponse.setAccountInfo(modelMapper.map(newUser, AccountInfoDto.class));
//
//        return registerResponse;
//    }

//    @Override
//    public LoginResponse login(LoginDto loginDto) {
//
//        String usernameOrEmail = loginDto.getUsernameOrEmail();
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        usernameOrEmail, loginDto.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setMessage("User logged in successfully!");
//        loginResponse.setAccessToken(jwtTokenProvider.generateToken(authentication));
//
//        return loginResponse;
//    }
}
