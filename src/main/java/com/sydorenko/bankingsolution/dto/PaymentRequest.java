package com.sydorenko.bankingsolution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Request object for payment operations")
public class PaymentRequest {

    @NotNull
    @Positive
    @Schema(description = "Amount of money to be processed", example = "100.00")
    private BigDecimal amount;
}