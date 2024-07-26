package com.vincennlin.userservice.controller;

import com.vincennlin.userservice.payload.*;
import com.vincennlin.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController implements UserControllerSwagger{

    private Environment env;

    private UserService userService;

    @GetMapping("/users/status/check")
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("User Service is up and running on port: " + env.getProperty("local.server.port")
                + " with token " + env.getProperty("token.secret"), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = {"/auth/register"})
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterDto registerDto) {

        RegisterResponse registerResponse = userService.register(registerDto);
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping(value = {"/auth/login"})
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<UserDto> getUserByUserId(@PathVariable("user_id") Long userId) {

        UserDto userDto = userService.getUserByUserId(userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/users/info")
    public ResponseEntity<AccountInfoDto> getCurrentAccountInfo() {

        AccountInfoDto accountInfo = userService.getCurrentAccountInfo();

        return new ResponseEntity<>(accountInfo, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADVANCED')")
    @GetMapping("/users/all")
    public ResponseEntity<List<AccountInfoDto>> getAllUsers() {

        List<AccountInfoDto> accountInfos = userService.getAllUsers();

        return new ResponseEntity<>(accountInfos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/users/info")
    public ResponseEntity<UpdateAccountInfoResponse> updateAccountInfo(@Valid @RequestBody AccountInfoDto accountInfoDto) {

        UpdateAccountInfoResponse updateAccountInfoResponse = userService.updateAccountInfo(accountInfoDto);

        return new ResponseEntity<>(updateAccountInfoResponse, HttpStatus.OK);
    }
}
