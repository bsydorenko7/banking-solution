package com.sydorenko.bankingsolution.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AccountDto {

    private UUID id;

    @NotNull
    @Size(min = 1, max = 100)
    private String accountOwnerName;

    @NotNull
    @Positive
    private BigDecimal balance;
}
