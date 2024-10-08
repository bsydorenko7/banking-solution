package com.sydorenko.bankingsolution.mapper;

import com.sydorenko.bankingsolution.dto.AccountDto;
import com.sydorenko.bankingsolution.entity.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto) {
        return new Account(
                accountDto.getId(),
                accountDto.getAccountOwnerName(),
                accountDto.getBalance()
        );
    }

    public static AccountDto mapToAccountDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getAccountOwnerName(),
                account.getBalance()
        );
    }
}
