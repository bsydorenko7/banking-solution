package com.sydorenko.bankingsolution.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Error response structure")
public class ErrorResponse {

    @Schema(description = "Error code", example = "400")
    private final int statusCode;
    @Schema(description = "Error message", example = "Bad request")
    private final String message;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
