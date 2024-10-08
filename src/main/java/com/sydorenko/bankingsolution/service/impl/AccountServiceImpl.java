package com.sydorenko.bankingsolution.service.impl;

import com.sydorenko.bankingsolution.dto.AccountDto;
import com.sydorenko.bankingsolution.entity.Account;
import com.sydorenko.bankingsolution.exception.AccountNotFoundException;
import com.sydorenko.bankingsolution.mapper.AccountMapper;
import com.sydorenko.bankingsolution.exception.BadRequestException;
import com.sydorenko.bankingsolution.repository.AccountRepository;
import com.sydorenko.bankingsolution.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.sydorenko.bankingsolution.mapper.AccountMapper.mapToAccount;
import static com.sydorenko.bankingsolution.mapper.AccountMapper.mapToAccountDto;
import static com.sydorenko.bankingsolution.service.validation.AccountValidator.validateAccountForWithdrawalAmount;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto requestAccountDto) {
        log.info("Creating account for owner: {}", requestAccountDto.getAccountOwnerName());
        Account requestAccount = mapToAccount(requestAccountDto);
        Account savedAccount = accountRepository.save(requestAccount);
        log.info("Account created with ID: {}", savedAccount.getId());
        return mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(UUID id) {
        log.info("Fetching account with ID: {}", id);
        Account account = getAccountByIdOrThrow(id);
        log.info("Account retrieved: {}", account);
        return mapToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts(int page, int size) {
        log.info("Fetching all accounts, page: {}, size: {}", page, size);
        Page<Account> accounts = accountRepository.findAll(PageRequest.of(page, size));
        log.info("Total accounts retrieved: {}", accounts.getTotalElements());
        return accounts.stream().map(AccountMapper::mapToAccountDto).toList();
    }

    @Override
    public AccountDto deposit(UUID id, BigDecimal amount) {
        log.info("Depositing amount: {} to account ID: {}", amount, id);
        Account account = getAccountByIdOrThrow(id);
        Account updatedAccount = addAmountToAccountBalance(account, amount);
        log.info("New balance after deposit: {}", updatedAccount.getBalance());
        return mapToAccountDto(updatedAccount);
    }

    @Override
    public AccountDto withdraw(UUID id, BigDecimal amount) {
        log.info("Withdrawing amount: {} from account ID: {}", amount, id);
        Account account = getAccountByIdOrThrow(id);
        validateAccountForWithdrawalAmount(account, amount);
        Account updatedAccount = addAmountToAccountBalance(account, amount.negate());
        log.info("New balance after withdrawal: {}", updatedAccount.getBalance());
        return mapToAccountDto(updatedAccount);
    }

    @Override
    @Transactional
    public void transfer(UUID senderId, UUID recipientId, BigDecimal amount) {
        log.info("Transferring amount: {} from account ID: {} to account ID: {}", amount, senderId, recipientId);
        withdraw(senderId, amount);
        deposit(recipientId, amount);
        log.info("Transfer completed from account ID: {} to account ID: {}", senderId, recipientId);
    }

    private Account getAccountByIdOrThrow(UUID id) {
        if (id != null) {
            return accountRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Account not found with ID: {}", id);
                        return new AccountNotFoundException(id);
                    });
        }
        log.error("Attempted to fetch account with null ID.");
        throw new BadRequestException("Account id cannot be null.");
    }

    private Account addAmountToAccountBalance(Account account, BigDecimal amount) {
        BigDecimal total = account.getBalance().add(amount);
        account.setBalance(total);
        return accountRepository.save(account);
    }
}