package com.sydorenko.bankingsolution.service.validation;

import com.sydorenko.bankingsolution.entity.Account;
import com.sydorenko.bankingsolution.exception.InsufficientFundsException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.UUID;

import static com.sydorenko.bankingsolution.util.MiscTool.isLessThan;

@Slf4j
public class AccountValidator {

    public static void validateAccountForWithdrawalAmount(Account account, BigDecimal amount) {
        BigDecimal accountBalance = account.getBalance();
        if (isLessThan(accountBalance, amount)) {
            UUID accountId = account.getId();
            log.warn("Insufficient funds on the account balance with ID: {}", accountId);
            log.debug("Account with id: {}, balance: {}, amount for withdrawal: {}", accountId, accountBalance, amount);
            throw new InsufficientFundsException(accountId);
        }
    }
}
