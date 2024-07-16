package com.vincennlin.accountservice.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountInfoResponse {

    private String message;

    @JsonProperty(value = "account_info")
    private AccountInfoDto accountInfo;
}
