package com.bo.banking.financial.service;

import com.bo.banking.financial.dao.TransactionDao;
import com.bo.banking.financial.dto.TransactionRequest;
import com.bo.banking.financial.dto.enums.FilterRequest;
import com.bo.banking.financial.model.Account;
import com.bo.banking.financial.model.Transaction;
import com.bo.banking.financial.service.mapper.TransactionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static com.bo.banking.utils.RandomUtils.randomAccount;
import static com.bo.banking.utils.RandomUtils.randomLocalDate;
import static com.bo.banking.utils.RandomUtils.randomTransaction;
import static com.bo.banking.utils.RandomUtils.randomTransactionRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private TransactionDao transactionDao;
    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private TransactionService transactionService;

    @Test
    void getById() {
        //given
        Transaction transaction = randomTransaction();
        when(transactionDao.findById(transaction.getId())).thenReturn(Optional.of(transaction));

        //when
        Transaction result = transactionService.getById(transaction.getId());

        //then
        assertThat(result.getId()).isEqualTo(transaction.getId());
    }

    @Test
    void add() {
        //given
        TransactionRequest request = randomTransactionRequest();
        Transaction transaction = mock(Transaction.class);
        Account fromAccount = randomAccount();
        Account toAccount = randomAccount();

        when(transactionMapper.map(request)).thenReturn(transaction);
        when(accountService.findEntityById(request.getFromAccountId())).thenReturn(fromAccount);
        when(accountService.findEntityById(request.getToAccountId())).thenReturn(toAccount);

        //when
        transactionService.add(request);

        //then
        verify(transactionDao, times(1)).add(transaction);
        verify(transactionMapper, times(1)).map(request);
        verify(transactionMapper, times(1)).mapToDto(transaction);
        verify(accountService, times(2)).findEntityById(any());
        verify(accountService, times(2)).update(any());
    }

    @Test
    void delete() {
        //given
        Transaction transaction = randomTransaction();
        when(transactionDao.findById(transaction.getId())).thenReturn(Optional.of(transaction));

        //when
        transactionService.delete(transaction.getId());

        //then
        verify(transactionDao).delete(transaction.getId());
    }

    @Test
    void filterAccountTransactionsByDate() {
        //given
        Transaction transaction = randomTransaction();
        LocalDate localDate = randomLocalDate();
        LocalDateTime localDateTime = localDate.atStartOfDay();
        FilterRequest request = new FilterRequest();
        request.setAccountId(transaction.getFromAccount().getId());
        request.setDayFrom(localDate);
        request.setDayTo(localDate);
        when(transactionDao.selectTransactions(transaction.getFromAccount().getId(), localDateTime, localDateTime.plusDays(1))).thenReturn(Collections.singletonList(transaction));

        //when
        transactionService.filterAccountTransactionsByDate(request);

        //then
        verify(transactionMapper, times(1)).mapToDtoWithType(transaction, request.getAccountId());
    }

    @Test
    void getTransactionsByAccountId() {
        //given
        Transaction transaction = randomTransaction();
        Account account = randomAccount();
        when(transactionDao.selectTransactionsByAccountId(account.getId())).thenReturn(Collections.singletonList(transaction));

        //when
        transactionService.getTransactionsByAccountId(account.getId());

        //then
        verify(transactionMapper, times(1)).mapToDtoWithType(transaction, account.getId());
    }
}