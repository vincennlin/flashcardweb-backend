package com.vincennlin.flashcardwebbackend.service;

import com.vincennlin.flashcardwebbackend.payload.account.AccountInfoDto;
import com.vincennlin.flashcardwebbackend.payload.account.UpdateAccountInfoResponse;

import java.util.List;

public interface AccountService {

    AccountInfoDto getCurrentAccountInfo();

    List<AccountInfoDto> getAllUsers();

    UpdateAccountInfoResponse updateAccountInfo(AccountInfoDto accountInfoDto);
}