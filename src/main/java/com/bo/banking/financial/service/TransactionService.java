package com.bo.banking.financial.service;

import com.bo.banking.exception.NotFoundException;
import com.bo.banking.financial.dao.TransactionDao;
import com.bo.banking.financial.dto.TransactionDto;
import com.bo.banking.financial.dto.TransactionRequest;
import com.bo.banking.financial.dto.enums.FilterRequest;
import com.bo.banking.financial.model.Account;
import com.bo.banking.financial.model.Transaction;
import com.bo.banking.financial.service.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {
    private final TransactionDao transactionDao;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;

    public Transaction getById(Long id) {
        return transactionDao.findById(id).orElseThrow(() -> new NotFoundException(Transaction.class, id));
    }

    public TransactionDto add(TransactionRequest request) {
        Transaction transaction = transactionMapper.map(request);
        transactionDao.add(transaction);
        deposit(request.getToAccountId(), request.getAmount());
        withdraw(request.getFromAccountId(), request.getAmount());

        return transactionMapper.mapToDto(transaction);
    }

    public void delete(Long id) {
        getById(id);
        transactionDao.delete(id);
    }

    public List<TransactionDto> filterAccountTransactionsByDate(FilterRequest request) {
        return transactionDao.selectTransactions(request.getAccountId(), request.getDayFrom().atStartOfDay(), request.getDayTo().plusDays(1).atStartOfDay()).stream()
                .map(transaction -> transactionMapper.mapToDtoWithType(transaction, request.getAccountId()))
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getTransactionsByAccountId(Long accountId) {
        return transactionDao.selectTransactionsByAccountId(accountId).stream()
                .map(transaction -> transactionMapper.mapToDtoWithType(transaction, accountId))
                .collect(Collectors.toList());
    }

    private void deposit(Long toAccountId, BigDecimal amount) {
        Account account = accountService.findEntityById(toAccountId);
        account.setBalance(account.getBalance().add(amount));
        accountService.update(account);
    }

    private void withdraw(Long fromAccountId, BigDecimal amount) {
        Account account = accountService.findEntityById(fromAccountId);
        account.setBalance(account.getBalance().subtract(amount));
        accountService.update(account);
    }
}
