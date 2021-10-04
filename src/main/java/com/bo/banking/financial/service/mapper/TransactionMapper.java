package com.bo.banking.financial.service.mapper;


import com.bo.banking.financial.dto.TransactionDto;
import com.bo.banking.financial.dto.TransactionRequest;
import com.bo.banking.financial.model.Transaction;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(TransactionMapperDecorator.class)
public interface TransactionMapper {
    Transaction map(TransactionRequest request);

    @Mapping(target = "fromAccountId", source = "fromAccount.id")
    @Mapping(target = "toAccountId", source = "toAccount.id")
    TransactionDto mapToDto(Transaction transaction);

    TransactionDto mapToDtoWithType(Transaction transaction, Long accountId);
}
