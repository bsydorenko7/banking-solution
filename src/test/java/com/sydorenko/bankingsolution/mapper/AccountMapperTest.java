package com.sydorenko.bankingsolution.mapper;

import com.sydorenko.bankingsolution.dto.AccountDto;
import com.sydorenko.bankingsolution.entity.Account;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountMapperTest {

    private static final UUID ACCOUNT_ID = UUID.randomUUID();
    private static final String ACCOUNT_OWNER_NAME_1 = "John Doe";
    private static final String ACCOUNT_OWNER_NAME_2 = "Jane Doe";
    private static final BigDecimal INITIAL_BALANCE_1 = BigDecimal.valueOf(1000);
    private static final BigDecimal INITIAL_BALANCE_2 = BigDecimal.valueOf(2000);

    @Test
    void mapToAccount_shouldReturnAccount_whenAccountDtoIsProvided() {
        AccountDto accountDto = new AccountDto(ACCOUNT_ID, ACCOUNT_OWNER_NAME_1, INITIAL_BALANCE_1);

        Account account = AccountMapper.mapToAccount(accountDto);

        assertEquals(ACCOUNT_ID, account.getId());
        assertEquals(ACCOUNT_OWNER_NAME_1, account.getAccountOwnerName());
        assertEquals(INITIAL_BALANCE_1, account.getBalance());
    }

    @Test
    void mapToAccountDto_shouldReturnAccountDto_whenAccountIsProvided() {
        Account account = new Account(ACCOUNT_ID, ACCOUNT_OWNER_NAME_2, INITIAL_BALANCE_2);

        AccountDto accountDto = AccountMapper.mapToAccountDto(account);

        assertEquals(ACCOUNT_ID, accountDto.getId());
        assertEquals(ACCOUNT_OWNER_NAME_2, accountDto.getAccountOwnerName());
        assertEquals(INITIAL_BALANCE_2, accountDto.getBalance());
    }
}
