package com.sydorenko.bankingsolution.controller;

import com.sydorenko.bankingsolution.dto.AccountDto;
import com.sydorenko.bankingsolution.dto.PaymentRequest;
import com.sydorenko.bankingsolution.dto.TransferRequest;
import com.sydorenko.bankingsolution.service.AccountService;
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
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        validatePageSize(size);
        return ResponseEntity.ok(accountService.getAllAccounts(page, size));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable UUID id, @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(accountService.deposit(id, paymentRequest.getAmount()));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable UUID id, @Valid @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(accountService.withdraw(id, paymentRequest.getAmount()));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@Valid @RequestBody TransferRequest transferRequest) {
        accountService.transfer(transferRequest.getSenderId(),
                transferRequest.getRecipientId(), transferRequest.getAmount());
        return ResponseEntity.ok("Transfer successful");
    }
}