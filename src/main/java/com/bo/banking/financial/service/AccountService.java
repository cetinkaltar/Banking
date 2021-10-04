package com.bo.banking.financial.service;

import com.bo.banking.exception.NotFoundException;
import com.bo.banking.financial.dao.AccountDao;
import com.bo.banking.financial.dto.AccountRequest;
import com.bo.banking.financial.dto.enums.AccountDto;
import com.bo.banking.financial.model.Account;
import com.bo.banking.financial.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountDao accountDao;
    private final AccountMapper accountMapper;

    public AccountDto findById(Long id) {
        return accountMapper.mapToDto(accountDao.selectAccount(id).orElseThrow(() -> new NotFoundException(Account.class, id)));
    }

    public Account findEntityById(Long id) {
        Account account = accountDao.selectAccount(id).orElseThrow(() -> new NotFoundException(Account.class, id));
        return account;
    }

    public AccountDto add(AccountRequest request) {
        Account account = accountMapper.map(request);
        accountDao.add(account);

        return accountMapper.mapToDto(account);
    }

    public AccountDto update(Account account) {
        accountDao.update(account);

        return accountMapper.mapToDto(account);
    }

    public void delete(Long id) {
        findEntityById(id);
        accountDao.delete(id);
    }

    public List<AccountDto> findAllByCustomerId(Long id) {
        return accountDao.findAllByCustomerId(id).stream()
                .map(accountMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
