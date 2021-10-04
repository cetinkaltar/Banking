package com.bo.banking.customer.dto;

import com.bo.banking.financial.dto.enums.AccountDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerDto extends CustomerRequest {
    private Long id;
    private List<AccountDto> accounts;
}
