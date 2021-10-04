package com.bo.banking.financial.service.mapper;

import com.bo.banking.financial.dao.AccountDao;
import com.bo.banking.financial.dto.TransactionDto;
import com.bo.banking.financial.dto.TransactionRequest;
import com.bo.banking.financial.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.bo.banking.financial.dto.enums.PaymentType.INCOMING;
import static com.bo.banking.financial.dto.enums.PaymentType.OUTGOING;

public abstract class TransactionMapperDecorator implements TransactionMapper {
    private TransactionMapper delegate;
    private AccountDao accountDao;

    @Autowired
    public void setDelegate(@Qualifier("delegate") TransactionMapper delegate) {
        this.delegate = delegate;
    }

    @Autowired
    public void setAccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Transaction map(TransactionRequest source) {
        Transaction target = delegate.map(source);
        if (Objects.isNull(target)) {
            return null;
        }
        target.setFromAccount(accountDao.selectAccount(source.getFromAccountId()).get());
        target.setToAccount(accountDao.selectAccount(source.getToAccountId()).get());
        target.setDate(LocalDateTime.now());

        return target;
    }

    public TransactionDto mapToDtoWithType(Transaction source, Long accountId) {
        TransactionDto target = delegate.mapToDto(source);
        if (Objects.isNull(target)) {
            return null;
        }
        if (source.getFromAccount().getId().equals(accountId)) {
            target.setType(OUTGOING);
        } else if (source.getToAccount().getId().equals(accountId)) {
            target.setType(INCOMING);
        }

        return target;
    }
}
