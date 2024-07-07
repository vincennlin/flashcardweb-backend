package com.vincennlin.flashcardwebbackend.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "UserDto",
        description = "用戶的 Data Transfer Object"
)
public class UserDto {

    @Schema(
            name = "id",
            description = "用戶 id",
            example = "1"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(
            name = "name",
            description = "用戶暱稱",
            example = "user"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;

    @Schema(
            name = "username",
            description = "用戶帳號名稱",
            example = "user"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String username;

    @Schema(
            name = "email",
            description = "用戶電子郵件",
            example = "user@gmail.com"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String email;

    @Schema(
            name = "roles",
            description = "用戶身份列表",
            example = "[{\"name\": \"ROLE_USER\"}]"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<RoleDto> roles;
}