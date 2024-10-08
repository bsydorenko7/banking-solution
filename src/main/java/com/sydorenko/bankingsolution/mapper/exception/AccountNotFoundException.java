package com.sydorenko.bankingsolution.mapper.exception;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(UUID accountId) {
        super("Not found account with id: " + accountId);
    }
}
