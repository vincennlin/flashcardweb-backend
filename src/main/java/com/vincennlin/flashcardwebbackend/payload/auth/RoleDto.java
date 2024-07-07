package com.vincennlin.flashcardwebbackend.payload.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
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
        name = "RoleDto",
        description = "用戶身份的 Data Transfer Object"
)
public class RoleDto {

    @Schema(
            name = "name",
            description = "用戶身份名稱",
            example = "ROLE_USER"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;
}