package com.bo.banking.financial.service;

import com.bo.banking.financial.dao.AccountDao;
import com.bo.banking.financial.dto.AccountRequest;
import com.bo.banking.financial.model.Account;
import com.bo.banking.financial.service.mapper.AccountMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static com.bo.banking.utils.RandomUtils.randomLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountDao accountDao;
    @Mock
    private AccountMapper accountMapper;
    @InjectMocks
    private AccountService accountService;

    @Test
    public void findById() {
        //given
        Account account = new Account();
        account.setId(randomLong());
        when(accountDao.selectAccount(account.getId())).thenReturn(Optional.of(account));

        //when
        accountService.findById(account.getId());

        //then
        verify(accountMapper).mapToDto(account);
    }

    @Test
    public void findEntityById() {
        //given
        Account account = new Account();
        account.setId(randomLong());
        when(accountDao.selectAccount(account.getId())).thenReturn(Optional.of(account));

        //when
        Account result = accountService.findEntityById(account.getId());

        //then
        assertThat(result.getId()).isEqualTo(account.getId());
    }

    @Test
    public void add() {
        //given
        AccountRequest request = mock(AccountRequest.class);
        Account account = mock(Account.class);
        when(accountMapper.map(request)).thenReturn(account);

        //when
        accountService.add(request);

        //then
        verify(accountDao).add(account);
        verify(accountMapper).map(request);
        verify(accountMapper).mapToDto(account);
    }

    @Test
    public void update() {
        //given
        Account account = mock(Account.class);

        //when
        accountService.update(account);

        //then
        verify(accountDao).update(account);
        verify(accountMapper).mapToDto(account);
    }

    @Test
    void delete() {
        //given
        Account account = new Account();
        account.setId(randomLong());
        when(accountDao.selectAccount(account.getId())).thenReturn(Optional.of(account));

        //when
        accountService.delete(account.getId());

        //then
        verify(accountDao).delete(account.getId());
    }

    @Test
    void findAllByCustomerId() {
        //given
        Long customerId = randomLong();
        Account account = new Account();
        account.setId(randomLong());
        when(accountDao.findAllByCustomerId(customerId)).thenReturn(Collections.singletonList(account));

        //when
        accountService.findAllByCustomerId(customerId);

        //then
        verify(accountMapper, times(1)).mapToDto(account);
    }
}