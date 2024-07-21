package com.vincennlin.userservice.controller;

import com.vincennlin.userservice.payload.*;
import com.vincennlin.userservice.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "用戶相關 API",
        description = "用戶註冊、登入、登出相關的 API"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Flashcardweb user-ws API",
                version = "1.0",
                description = "Flashcardweb 用戶服務相關的 API",
                contact = @io.swagger.v3.oas.annotations.info.Contact(
                        name = "vincennlin",
                        email = "vincentagwa@gmail.com",
                        url = "https://github.com/vincennlin"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        externalDocs = @io.swagger.v3.oas.annotations.ExternalDocumentation(
                description = "Flashcard Web Backend API Documentation",
                url = "https://github.com/vincennlin/flashcardweb-backend"
        )
)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private Environment env;

    private UserService userService;

    @Operation(
            summary = "檢查用戶服務狀態",
            description = "檢查用戶服務是否正常運作"
    )
    @ApiResponse(
            responseCode = "200",
            description = "用戶服務正常運作",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "User Service is up and running on port 61827")
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @GetMapping("/users/status/check")
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("User Service is up and running on port: " + env.getProperty("local.server.port")
                + " with token " + env.getProperty("token.secret"), HttpStatus.OK);
    }

    @Operation(
            summary = "註冊用戶",
            description = "註冊一個新用戶，注意 username 和 email 都不可和其他用戶重複，password 至少 4 個字元"
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
                                    "date_created": "2024-07-21T21:58:12.642225",
                                    "last_updated": "2024-07-21T21:58:12.642802"
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
            description = "登入成功，Response Header 的 'access_token' 欄位會包含 JWT Token，'user_id' 會包含用戶的 id"
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

    @Operation(
            summary = "取得用戶資訊",
            description = "根據用戶 id 取得用戶資訊"
    )
    @ApiResponse(
            responseCode = "200",
            description = "取得用戶資訊",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "name": "user",
                                "username": "user",
                                "email": "user@gmail.com",
                                "date_created": "2024-07-21T21:59:34.451019",
                                "last_updated": "2024-07-21T21:59:34.45108"
                            }
                            """
                    )
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @GetMapping("/users/{user_id}")
    public ResponseEntity<UserDto> getUserByUserId(@PathVariable("user_id") Long userId) {

        UserDto userDto = userService.getUserByUserId(userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Operation(
            summary = "取得目前帳號資訊",
            description = "根據 JWT Token 取得目前登入的用戶資訊"
    )
    @ApiResponse(
            responseCode = "200",
            description = "取得目前帳號資訊",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "name": "user",
                                "username": "user",
                                "email": "user@gmail.com",
                                "date_created": "2024-07-21T21:59:34.451019",
                                "last_updated": "2024-07-21T21:59:34.45108"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/users/info")
    public ResponseEntity<AccountInfoDto> getCurrentAccountInfo() {

        AccountInfoDto accountInfo = userService.getCurrentAccountInfo();

        return new ResponseEntity<>(accountInfo, HttpStatus.OK);
    }

    @Operation(
            summary = "以 ADMIN 身份取得所有用戶",
            description = "取得所有用戶，只有 ADMIN 可以存取"
    )
    @ApiResponse(
            responseCode = "200",
            description = "取得所有用戶",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                 {
                                     "id": 1,
                                     "name": "admin",
                                     "username": "admin",
                                     "email": "admin@gmail.com",
                                     "date_created": "2024-07-18T13:39:10.829251",
                                     "last_updated": "2024-07-18T13:39:10.829308"
                                 },
                                 {
                                     "id": 2,
                                     "name": "user",
                                     "username": "user",
                                     "email": "user@gmail.com",
                                     "date_created": "2024-07-18T13:41:31.917832",
                                     "last_updated": "2024-07-18T22:59:26.437294"
                                 },
                                 {
                                     "id": 3,
                                     "name": "test",
                                     "username": "test",
                                     "email": "test@gmail.com",
                                     "date_created": "2024-07-19T10:15:32.789745",
                                     "last_updated": "2024-07-19T10:15:32.790476"
                                 }
                            ]
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADVANCED')")
    @GetMapping("/users/all")
    public ResponseEntity<List<AccountInfoDto>> getAllUsers() {

        List<AccountInfoDto> accountInfos = userService.getAllUsers();

        return new ResponseEntity<>(accountInfos, HttpStatus.OK);
    }

    @Operation(
            summary = "更新目前帳號資訊",
            description = "更新目前帳號資訊"
    )
    @ApiResponse(
            responseCode = "200",
            description = "只要有更新 username, email 或 password 就需要重新登入",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "message": "User updated successfully! Please login again.",
                                "account_info": {
                                    "id": 1,
                                    "name": "user_updated",
                                    "username": "user_updated",
                                    "email": "user_updated@gmail.com",
                                    "date_created": "2024-07-21T21:59:34.451019",
                                    "last_updated": "2024-07-21T21:59:34.45108"
                                }
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/users/info")
    public ResponseEntity<UpdateAccountInfoResponse> updateAccountInfo(@Valid @RequestBody
                                                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                               content = @Content(
                                                                                       mediaType = "application/json",
                                                                                       examples = @ExampleObject(value = """
                                                                                                   {
                                                                                                       "name": "user_updated",
                                                                                                       "username": "user_updated",
                                                                                                       "email": "user_updated@gmail.com",
                                                                                                       "password": "user_updated"
                                                                                                   }
                                                                                                   """)
                                                                               )
                                                                       ) AccountInfoDto accountInfoDto) {

        UpdateAccountInfoResponse updateAccountInfoResponse = userService.updateAccountInfo(accountInfoDto);

        return new ResponseEntity<>(updateAccountInfoResponse, HttpStatus.OK);
    }
}
