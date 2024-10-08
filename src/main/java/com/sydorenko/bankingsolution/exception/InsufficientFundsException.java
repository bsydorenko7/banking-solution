package com.sydorenko.bankingsolution.exception;

import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(UUID accountId) {
        super("Insufficient funds on the account balance with id: " + accountId);
    }
}
