package com.bo.banking.financial.service.mapper;

import com.bo.banking.financial.dto.AccountRequest;
import com.bo.banking.financial.dto.enums.AccountDto;
import com.bo.banking.financial.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static com.bo.banking.utils.RandomUtils.randomAccount;
import static com.bo.banking.utils.RandomUtils.randomAccountRequest;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AccountMapperTest {
    @Mock
    private TransactionMapper transactionMapper;
    @InjectMocks
    private AccountMapperImpl_ delegate;
    @InjectMocks
    private AccountMapperImpl mapper;

    @BeforeEach
    public void setUp() {
        mapper.setDelegate(delegate);
        ReflectionTestUtils.setField(mapper, "delegate", delegate);
    }

    @Test
    public void map() {
        //given
        AccountRequest request = randomAccountRequest();

        //when
        Account result = mapper.map(request);

        //then
        assertThat(result.getCustomer().getId()).isEqualTo(request.getCustomerId());
        assertThat(result.getBalance()).isEqualByComparingTo(request.getBalance());
        assertThat(result.getIban()).isEqualTo(request.getIban());
    }

    @Test
    public void mapToDto() {
        //given
        Account account = randomAccount();

        //when
        AccountDto result = mapper.mapToDto(account);

        //then
        assertThat(result.getId()).isEqualTo(account.getId());
        assertThat(result.getIban()).isEqualTo(account.getIban());
        assertThat(result.getBalance()).isEqualByComparingTo(account.getBalance());
    }
}