package com.sydorenko.bankingsolution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@Schema(description = "Data transfer object for an account")
public class AccountDto {

    @Schema(description = "Unique identifier of the account", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @NotNull
    @Size(min = 1, max = 100)
    @Schema(description = "Name of the account owner", example = "John Doe")
    private String accountOwnerName;

    @NotNull
    @Positive
    @Schema(description = "Current balance of the account", example = "1000.00")
    private BigDecimal balance;
}