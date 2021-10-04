package com.bo.banking.financial.service.mapper;


import com.bo.banking.financial.dto.AccountRequest;
import com.bo.banking.financial.dto.enums.AccountDto;
import com.bo.banking.financial.model.Account;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TransactionMapper.class)
@DecoratedWith(AccountMapperDecorator.class)
public interface AccountMapper {
    Account map(AccountRequest source);

    @Mapping(target = "customerId", source = "customer.id")
    AccountDto mapToDto(Account source);
}
