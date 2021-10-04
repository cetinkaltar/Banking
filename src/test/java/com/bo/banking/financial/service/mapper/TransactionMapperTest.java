package com.bo.banking.financial.service.mapper;

import com.bo.banking.financial.dao.AccountDao;
import com.bo.banking.financial.dto.TransactionDto;
import com.bo.banking.financial.dto.TransactionRequest;
import com.bo.banking.financial.model.Account;
import com.bo.banking.financial.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static com.bo.banking.financial.dto.enums.PaymentType.OUTGOING;
import static com.bo.banking.utils.RandomUtils.randomAccount;
import static com.bo.banking.utils.RandomUtils.randomTransaction;
import static com.bo.banking.utils.RandomUtils.randomTransactionRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionMapperTest {
    @Mock
    private AccountDao accountDao;
    @InjectMocks
    private TransactionMapperImpl_ delegate;
    @InjectMocks
    private TransactionMapperImpl mapper;

    @BeforeEach
    public void setUp() {
        mapper.setDelegate(delegate);
        ReflectionTestUtils.setField(mapper, "delegate", delegate);
    }

    @Test
    public void map() {
        //given
        TransactionRequest request = randomTransactionRequest();

        Account fromAccount = randomAccount();
        fromAccount.setId(request.getFromAccountId());

        Account toAccount = randomAccount();
        toAccount.setId(request.getToAccountId());

        when(accountDao.selectAccount(request.getFromAccountId())).thenReturn(Optional.of(fromAccount));
        when(accountDao.selectAccount(request.getToAccountId())).thenReturn(Optional.of(toAccount));

        //when
        Transaction result = mapper.map(request);

        //then
        assertThat(result.getAmount()).isEqualTo(request.getAmount());
        assertThat(result.getFromAccount().getId()).isEqualTo(request.getFromAccountId());
        assertThat(result.getToAccount().getId()).isEqualTo(request.getToAccountId());
        assertThat(result.getDescription()).isEqualTo(request.getDescription());
    }

    @Test
    public void mapToDto() {
        //given
        Transaction transaction = randomTransaction();

        //when
        TransactionDto result = mapper.mapToDto(transaction);

        //then
        assertThat(result.getId()).isEqualTo(transaction.getId());
        assertThat(result.getAmount()).isEqualByComparingTo(transaction.getAmount());
        assertThat(result.getDescription()).isEqualTo(transaction.getDescription());
        assertThat(result.getFromAccountId()).isEqualTo(transaction.getFromAccount().getId());
        assertThat(result.getToAccountId()).isEqualTo(transaction.getToAccount().getId());
        assertThat(result.getDate()).isEqualTo(transaction.getDate());
    }

    @Test
    public void mapToDtoWithType() {
        //given
        Transaction transaction = randomTransaction();
        Account fromAccount = randomAccount();
        Account toAccount = randomAccount();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);

        //when
        TransactionDto result = mapper.mapToDtoWithType(transaction, transaction.getFromAccount().getId());

        //then
        assertThat(result.getId()).isEqualTo(transaction.getId());
        assertThat(result.getAmount()).isEqualByComparingTo(transaction.getAmount());
        assertThat(result.getDescription()).isEqualTo(transaction.getDescription());
        assertThat(result.getFromAccountId()).isEqualTo(transaction.getFromAccount().getId());
        assertThat(result.getToAccountId()).isEqualTo(transaction.getToAccount().getId());
        assertThat(result.getDate()).isEqualTo(transaction.getDate());
        assertThat(result.getType()).isEqualTo(OUTGOING);
    }
}