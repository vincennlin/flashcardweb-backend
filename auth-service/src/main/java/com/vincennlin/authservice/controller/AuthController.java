package com.vincennlin.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "帳號驗證 API",
        description = "用戶註冊、登入、登出相關的 API"
)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @GetMapping("/status/check")
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("Auth service is up and running", HttpStatus.OK);
    }

//    private AuthService authService;

//    @Operation(
//            summary = "註冊用戶",
//            description = "註冊一個新用戶注意 username 和 email 都不可和其他用戶重複，password 至少 4 個字元"
//    )
//    @ApiResponse(
//            responseCode = "201",
//            description = "註冊成功",
//            content = @Content(
//                    mediaType = "application/json",
//                    examples = @ExampleObject(value = """
//                            {
//                                "message": "User registered successfully!",
//                                "account_info": {
//                                    "id": 1,
//                                    "name": "user",
//                                    "username": "user",
//                                    "email": "user@gmail.com",
//                                    "roles": [
//                                        {
//                                            "name": "ROLE_USER"
//                                        }
//                                    ],
//                                    "date_created": "2024-07-07T12:20:13.712345",
//                                    "last_updated": "2024-07-07T12:20:13.712412"
//                                }
//                            }
//                            """)
//            )
//    )
//    @PostMapping(value = {"/register"})
//    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody
//                                                     @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                                                             content = @Content(
//                                                                     mediaType = "application/json",
//                                                                     examples = @ExampleObject(value = """
//                                                                                 {
//                                                                                     "name": "user",
//                                                                                     "username": "user",
//                                                                                     "email": "user@gmail.com",
//                                                                                     "password": "user"
//                                                                                 }
//                                                                                 """)
//                                                             )
//                                                     ) RegisterDto registerDto) {
//
//        RegisterResponse registerResponse = authService.register(registerDto);
//        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
//    }
//
//    @Operation(
//            summary = "登入",
//            description = "登入用戶"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "登入成功，Response Body 會包含 JWT Token",
//            content = @Content(
//                    mediaType = "application/json",
//                    examples = @ExampleObject(value = """
//                            {
//                                "message": "User logged in successfully!",
//                                "access_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzIwMzI2MDcwLCJleHAiOjE3MjA5MzA4NzB9.hw2pFQvt5gzYoJMPNOR3lj_S0uStnpXbX-rXmib7ldzt5elULb_jguHHsOIaNhva",
//                                "token_type": "Bearer"
//                            }
//                            """)
//            )
//    )
//    @PostMapping(value = {"/login"})
//    public ResponseEntity<LoginResponse> login(@Valid @RequestBody
//                                               @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                                                       content = @Content(
//                                                               mediaType = "application/json",
//                                                               examples = @ExampleObject(value = """
//                                                                           {
//                                                                               "username_or_email": "user",
//                                                                               "password": "user"
//                                                                           }
//                                                                           """)
//                                                       )
//                                               ) LoginDto loginDto) {
//
//        LoginResponse loginResponse = authService.login(loginDto);
//        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
//    }
}
