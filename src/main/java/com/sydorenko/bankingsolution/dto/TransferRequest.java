package com.sydorenko.bankingsolution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Request object for transferring funds between accounts")
public class TransferRequest extends PaymentRequest {

    @NotNull
    @Schema(description = "ID of the account sending the funds", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private UUID senderId;

    @NotNull
    @Schema(description = "ID of the account receiving the funds", example = "f47ac10b-58cc-4372-a567-0e02b2c3d490")
    private UUID recipientId;
}