package com.bo.banking.customer.service.mapper;


import com.bo.banking.customer.dto.CustomerDto;
import com.bo.banking.customer.dto.CustomerRequest;
import com.bo.banking.customer.model.Customer;
import com.bo.banking.financial.service.mapper.AccountMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface CustomerMapper {
    CustomerDto mapToDto(Customer source);

    void update(@MappingTarget Customer customer, CustomerRequest request);

    Customer map(CustomerRequest request);
}
