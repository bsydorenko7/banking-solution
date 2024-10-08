package com.sydorenko.bankingsolution.mapper.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int statusCode;
    private final String message;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
