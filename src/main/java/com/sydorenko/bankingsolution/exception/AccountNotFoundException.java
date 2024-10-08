package com.sydorenko.bankingsolution.exception;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(UUID accountId) {
        super("Not found account with id: " + accountId);
    }
}
