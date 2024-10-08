package com.sydorenko.bankingsolution.controller;

import com.sydorenko.bankingsolution.dto.AccountDto;
import com.sydorenko.bankingsolution.dto.PaymentRequest;
import com.sydorenko.bankingsolution.dto.TransferRequest;
import com.sydorenko.bankingsolution.exception.ErrorResponse;
import com.sydorenko.bankingsolution.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.sydorenko.bankingsolution.service.validation.PageSizeValidator.validatePageSize;

@RestController
@RequestMapping("/api/v1/banking-management/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new account",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Data for creating an account",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Account successfully created"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get account by ID",
            parameters = @Parameter(name = "id", description = "Account ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved account",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "404", description = "Account not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<AccountDto> getAccountById(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping
    @Operation(
            summary = "Get a list of all accounts",
            parameters = {
                    @Parameter(name = "page", description = "Page number", required = false, example = "0"),
                    @Parameter(name = "size", description = "Page size", required = false, example = "20")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of accounts",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request due to invalid page size",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<List<AccountDto>> getAllAccounts(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        validatePageSize(size);
        return ResponseEntity.ok(accountService.getAllAccounts(page, size));
    }

    @PostMapping("/{id}/deposit")
    @Operation(
            summary = "Deposit to an account",
            parameters = @Parameter(name = "id", description = "Account ID", required = true),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Data for depositing to an account",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Account successfully deposited",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "404", description = "Account not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<AccountDto> deposit(@PathVariable UUID id, @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(accountService.deposit(id, paymentRequest.getAmount()));
    }

    @PostMapping("/{id}/withdraw")
    @Operation(
            summary = "Withdraw from an account",
            parameters = @Parameter(name = "id", description = "Account ID", required = true),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Data for withdrawing from an account",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Funds successfully withdrawn",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "404", description = "Account not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request or insufficient funds",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "422", description = "Insufficient funds for withdrawal",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<AccountDto> withdraw(@PathVariable UUID id, @Valid @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(accountService.withdraw(id, paymentRequest.getAmount()));
    }

    @PostMapping("/transfer")
    @Operation(
            summary = "Transfer funds between accounts",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Data for transferring funds",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TransferRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transfer successful"),
                    @ApiResponse(responseCode = "400", description = "Bad request or insufficient funds",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Account not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "422", description = "Insufficient funds for transfer",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<String> transfer(@Valid @RequestBody TransferRequest transferRequest) {
        accountService.transfer(transferRequest.getSenderId(),
                transferRequest.getRecipientId(), transferRequest.getAmount());
        return ResponseEntity.ok("Transfer successful");
    }
}