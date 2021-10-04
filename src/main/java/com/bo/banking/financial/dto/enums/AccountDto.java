package com.bo.banking.financial.dto.enums;

import com.bo.banking.financial.dto.AccountRequest;
import com.bo.banking.financial.dto.TransactionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountDto extends AccountRequest {
    private Long id;
    private List<TransactionDto> transactions;
}
