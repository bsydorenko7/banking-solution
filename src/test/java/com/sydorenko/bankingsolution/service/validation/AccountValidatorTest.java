package com.sydorenko.bankingsolution.service.validation;

import com.sydorenko.bankingsolution.entity.Account;
import com.sydorenko.bankingsolution.exception.InsufficientFundsException;
import com.sydorenko.bankingsolution.service.validation.AccountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountValidatorTest {

    private Account account;

    @BeforeEach
    void setUp() {
        UUID accountId = UUID.randomUUID();
        account = new Account();
        account.setId(accountId);
        account.setBalance(BigDecimal.valueOf(1000));
    }

    @Test
    void validateAccountForWithdrawalAmount_shouldNotThrowException_whenBalanceIsSufficient() {
        BigDecimal withdrawalAmount = BigDecimal.valueOf(500);
        assertDoesNotThrow(() -> AccountValidator.validateAccountForWithdrawalAmount(account, withdrawalAmount));
    }

    @Test
    void validateAccountForWithdrawalAmount_shouldThrowInsufficientFundsException_whenBalanceIsInsufficient() {
        BigDecimal withdrawalAmount = BigDecimal.valueOf(1500);
        assertThrows(InsufficientFundsException.class, () ->
                AccountValidator.validateAccountForWithdrawalAmount(account, withdrawalAmount)
        );
    }
}
