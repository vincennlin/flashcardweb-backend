package com.vincennlin.userservice.controller;

import com.vincennlin.userservice.payload.*;
import com.vincennlin.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "帳號驗證 API",
        description = "用戶註冊、登入、登出相關的 API"
)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private Environment env;

    @GetMapping("/users/status/check")
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("User Service is up and running on port: " + env.getProperty("local.server.port")
                + " with token " + env.getProperty("token.secret"), HttpStatus.OK);
    }

    private UserService userService;

    @Operation(
            summary = "註冊用戶",
            description = "註冊一個新用戶注意 username 和 email 都不可和其他用戶重複，password 至少 4 個字元"
    )
    @ApiResponse(
            responseCode = "201",
            description = "註冊成功",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "message": "User registered successfully!",
                                "account_info": {
                                    "id": 1,
                                    "name": "user",
                                    "username": "user",
                                    "email": "user@gmail.com",
                                    "roles": [
                                        {
                                            "name": "ROLE_USER"
                                        }
                                    ],
                                    "date_created": "2024-07-07T12:20:13.712345",
                                    "last_updated": "2024-07-07T12:20:13.712412"
                                }
                            }
                            """)
            )
    )
    @PostMapping(value = {"/auth/register"})
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody
                                                     @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                             content = @Content(
                                                                     mediaType = "application/json",
                                                                     examples = @ExampleObject(value = """
                                                                                 {
                                                                                     "name": "user",
                                                                                     "username": "user",
                                                                                     "email": "user@gmail.com",
                                                                                     "password": "user"
                                                                                 }
                                                                                 """)
                                                             )
                                                     ) RegisterDto registerDto) {

        RegisterResponse registerResponse = userService.register(registerDto);
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "登入",
            description = "登入用戶"
    )
    @ApiResponse(
            responseCode = "200",
            description = "登入成功，Response Header 會包含 JWT Token"
    )
    @PostMapping(value = {"/auth/login"})
    public ResponseEntity<Void> login(@Valid @RequestBody
                                               @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                       content = @Content(
                                                               mediaType = "application/json",
                                                               examples = @ExampleObject(value = """
                                                                           {
                                                                               "username_or_email": "user",
                                                                               "password": "user"
                                                                           }
                                                                           """)
                                                       )
                                               ) LoginDto loginDto) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<UserDto> getUserByUserId(@PathVariable("user_id") Long userId) {

        UserDto userDto = userService.getUserByUserId(userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
