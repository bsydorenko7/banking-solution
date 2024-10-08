package com.sydorenko.bankingsolution.service;

import com.sydorenko.bankingsolution.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountDto createAccount(AccountDto requestAccountDto);

    AccountDto getAccountById(UUID id);

    List<AccountDto> getAllAccounts(int page, int size);

    AccountDto deposit(UUID id, BigDecimal amount);

    AccountDto withdraw(UUID id, BigDecimal amount);

    void transfer(UUID senderId, UUID recipientId, BigDecimal amount);
}
