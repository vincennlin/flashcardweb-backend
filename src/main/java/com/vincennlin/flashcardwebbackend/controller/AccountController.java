package com.vincennlin.flashcardwebbackend.controller;

import com.vincennlin.flashcardwebbackend.payload.account.AccountInfoDto;
import com.vincennlin.flashcardwebbackend.payload.account.UpdateAccountInfoResponse;
import com.vincennlin.flashcardwebbackend.payload.user.UserDto;
import com.vincennlin.flashcardwebbackend.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "帳號管理 API",
        description = "用戶帳號管理的 API"
)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private AccountService accountService;

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
                                "roles": [
                                    {
                                        "name": "ROLE_USER"
                                    }
                                ],
                                "date_created": "2024-07-07T12:20:13.712345",
                                "last_updated": "2024-07-07T12:20:13.712412"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/info")
    public ResponseEntity<AccountInfoDto> getCurrentAccountInfo() {

        AccountInfoDto accountInfo = accountService.getCurrentAccountInfo();
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
                                },
                                {
                                    "id": 2,
                                    "name": "admin",
                                    "username": "admin",
                                    "email": "admin@gmail.com",
                                    "roles": [
                                        {
                                            "name": "ROLE_ADMIN"
                                        }
                                    ],
                                    "date_created": "2024-07-07T12:21:01.971015",
                                    "last_updated": "2024-07-07T12:21:01.971034"
                                },
                                {
                                    "id": 3,
                                    "name": "test",
                                    "username": "test",
                                    "email": "test@gmail.com",
                                    "roles": [
                                        {
                                            "name": "ROLE_USER"
                                        }
                                    ],
                                    "date_created": "2024-07-07T12:21:06.531122",
                                    "last_updated": "2024-07-07T12:21:06.531217"
                                }
                            ]
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/all")
    public ResponseEntity<List<AccountInfoDto>> getAllUsers() {

        List<AccountInfoDto> accountInfos = accountService.getAllUsers();

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
                                                    "roles": [
                                                        {
                                                            "name": "ROLE_USER"
                                                        }
                                                    ],
                                                    "date_created": "2024-07-07T13:33:15.659823",
                                                    "last_updated": "2024-07-07T13:37:35.066182"
                                                }
                                            }
                                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/info")
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

        UpdateAccountInfoResponse updateAccountInfoResponse = accountService.updateAccountInfo(accountInfoDto);
        return new ResponseEntity<>(updateAccountInfoResponse, HttpStatus.OK);
    }
}