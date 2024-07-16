package com.vincennlin.authservice.service.impl;


import com.vincennlin.accountservice.entity.User;
import com.vincennlin.authservice.exception.WebAPIException;
import com.vincennlin.authservice.payload.LoginDto;
import com.vincennlin.authservice.payload.LoginResponse;
import com.vincennlin.authservice.payload.RegisterDto;
import com.vincennlin.authservice.payload.RegisterResponse;
import com.vincennlin.authservice.repository.UserRepository;
import com.vincennlin.authservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

//    private AuthenticationManager authenticationManager;

//    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

//    private JwtTokenProvider jwtTokenProvider;
//
//    private ModelMapper modelMapper;

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
//        registerResponse.setAccountInfo(modelMapper.map(newUser, AccountInfoDto.class));

        return registerResponse;
    }

    @Override
    public LoginResponse login(LoginDto loginDto) {
        return null;
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
