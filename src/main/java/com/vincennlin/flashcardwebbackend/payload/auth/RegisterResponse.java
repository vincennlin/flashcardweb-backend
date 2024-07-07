package com.vincennlin.flashcardwebbackend.payload.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "RegisterResponse",
        description = "註冊回應的 Data Transfer Object"
)
public class RegisterResponse {

    @Schema(
            name = "message",
            description = "註冊回應訊息",
            example = "User registered successfully!"
    )
    private String message;

    @Schema(
            name = "user",
            description = "註冊成功的使用者資訊",
            example = "{\"id\": 1, \"name\": \"user\", \"username\": \"user\", \"email\": \"user@gmail.com\", \"roles\": [{\"name\": \"ROLE_USER\"}]}"
    )
    private UserDto user;
}
