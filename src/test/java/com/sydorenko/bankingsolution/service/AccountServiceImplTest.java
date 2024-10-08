package com.sydorenko.bankingsolution.service;

import com.sydorenko.bankingsolution.dto.AccountDto;
import com.sydorenko.bankingsolution.entity.Account;
import com.sydorenko.bankingsolution.exception.AccountNotFoundException;
import com.sydorenko.bankingsolution.exception.InsufficientFundsException;
import com.sydorenko.bankingsolution.repository.AccountRepository;
import com.sydorenko.bankingsolution.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;
    private AccountDto accountDto;
    private UUID accountId;

    private static final String ACCOUNT_OWNER_NAME = "John Doe";
    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(1000);
    private static final BigDecimal DEPOSIT_AMOUNT = BigDecimal.valueOf(500);
    private static final BigDecimal WITHDRAW_AMOUNT = BigDecimal.valueOf(500);
    private static final BigDecimal TRANSFER_AMOUNT = BigDecimal.valueOf(500);
    private static final BigDecimal INSUFFICIENT_FUNDS = BigDecimal.valueOf(1500);

    @BeforeEach
    void setUp() {
        accountId = UUID.randomUUID();
        account = new Account();
        account.setId(accountId);
        account.setBalance(INITIAL_BALANCE);

        accountDto = new AccountDto(accountId, ACCOUNT_OWNER_NAME, INITIAL_BALANCE);
    }

    @Test
    void createAccount_shouldReturnAccountDto() {
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDto result = accountService.createAccount(accountDto);

        assertNotNull(result);
        assertEquals(accountId, result.getId());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void getAccountById_shouldReturnAccountDto() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        AccountDto result = accountService.getAccountById(accountId);

        assertNotNull(result);
        assertEquals(accountId, result.getId());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void getAccountById_shouldThrowAccountNotFoundException() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountById(accountId));
    }

    @Test
    void getAllAccounts_shouldReturnListOfAccountDtos() {
        List<Account> accountList = List.of(account);
        Page<Account> accountPage = new PageImpl<>(accountList);
        when(accountRepository.findAll(any(PageRequest.class))).thenReturn(accountPage);

        List<AccountDto> result = accountService.getAllAccounts(0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(accountId, result.get(0).getId());
        verify(accountRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void deposit_shouldReturnUpdatedAccountDto() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDto result = accountService.deposit(accountId, DEPOSIT_AMOUNT);

        assertNotNull(result);
        assertEquals(accountId, result.getId());
        assertEquals(BigDecimal.valueOf(1500), result.getBalance());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void withdraw_shouldReturnUpdatedAccountDto() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDto result = accountService.withdraw(accountId, WITHDRAW_AMOUNT);

        assertNotNull(result);
        assertEquals(accountId, result.getId());
        assertEquals(BigDecimal.valueOf(500), result.getBalance());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void withdraw_shouldThrowAccountNotFoundException() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.withdraw(accountId, WITHDRAW_AMOUNT));
    }

    @Test
    void withdraw_shouldThrowInsufficientFundsExceptionForInvalidAmount() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(InsufficientFundsException.class, () -> accountService.withdraw(accountId, INSUFFICIENT_FUNDS));
    }

    @Test
    void transfer_shouldCompleteTransfer() {
        UUID recipientId = UUID.randomUUID();
        Account recipientAccount = new Account();
        recipientAccount.setId(recipientId);
        recipientAccount.setBalance(BigDecimal.valueOf(500));

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.findById(recipientId)).thenReturn(Optional.of(recipientAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(account).thenReturn(recipientAccount);

        accountService.transfer(accountId, recipientId, TRANSFER_AMOUNT);

        assertEquals(BigDecimal.valueOf(500), account.getBalance());
        assertEquals(BigDecimal.valueOf(1000), recipientAccount.getBalance());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).findById(recipientId);
        verify(accountRepository, times(2)).save(any(Account.class));
    }

    @Test
    void transfer_shouldThrowAccountNotFoundExceptionForSender() {
        UUID recipientId = UUID.randomUUID();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.transfer(accountId, recipientId, TRANSFER_AMOUNT));
    }

    @Test
    void transfer_shouldThrowAccountNotFoundExceptionForRecipient() {
        UUID recipientId = UUID.randomUUID();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.findById(recipientId)).thenReturn(Optional.empty());
        when(accountRepository.save(account)).thenReturn(account);

        assertThrows(AccountNotFoundException.class, () -> accountService.transfer(accountId, recipientId, TRANSFER_AMOUNT));
    }
}