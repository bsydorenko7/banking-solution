package com.sydorenko.bankingsolution.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransferRequest extends PaymentRequest {

    @NotNull
    private UUID senderId;

    @NotNull
    private UUID recipientId;
}
