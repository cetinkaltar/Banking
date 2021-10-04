package com.bo.banking.financial.service.mapper;

import com.bo.banking.customer.model.Customer;
import com.bo.banking.financial.dto.AccountRequest;
import com.bo.banking.financial.dto.enums.AccountDto;
import com.bo.banking.financial.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Objects;

import static com.bo.banking.financial.dto.enums.PaymentType.INCOMING;
import static com.bo.banking.financial.dto.enums.PaymentType.OUTGOING;

public abstract class AccountMapperDecorator implements AccountMapper {
    private AccountMapper delegate;

    @Autowired
    public void setDelegate(@Qualifier("delegate") AccountMapper delegate) {
        this.delegate = delegate;
    }

    public Account map(AccountRequest source) {
        Account target = delegate.map(source);
        if (Objects.isNull(target)) {
            return null;
        }

        Customer customer = new Customer();
        customer.setId(source.getCustomerId());
        target.setCustomer(customer);

        return target;
    }

    public AccountDto mapToDto(Account source) {
        AccountDto target = delegate.mapToDto(source);
        if (Objects.isNull(target)) {
            return null;
        }

       if (target.getTransactions() != null){
           target.getTransactions().stream()
                   .forEach(transactionDto -> {
                       if (transactionDto.getFromAccountId().equals(target.getId())) {
                           transactionDto.setType(OUTGOING);
                       } else if (transactionDto.getToAccountId().equals(target.getId())) {
                           transactionDto.setType(INCOMING);
                       }
                   });
       }

        return target;
    }

}
