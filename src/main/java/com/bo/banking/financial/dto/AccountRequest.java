package com.bo.banking.financial.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequest {
    private String iban;
    private BigDecimal balance;
    private Long customerId;
}
