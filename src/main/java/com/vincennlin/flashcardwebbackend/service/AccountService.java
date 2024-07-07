package com.vincennlin.flashcardwebbackend.service;

import com.vincennlin.flashcardwebbackend.payload.account.AccountInfoDto;

import java.util.List;

public interface AccountService {

    AccountInfoDto getCurrentAccountInfo();

    List<AccountInfoDto> getAllUsers();
}