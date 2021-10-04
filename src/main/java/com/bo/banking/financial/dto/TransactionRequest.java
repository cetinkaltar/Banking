package com.bo.banking.financial.dto;

import com.bo.banking.financial.validator.ValidTransactionRequest;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@ValidTransactionRequest
public class TransactionRequest {
    @NonNull
    private Long fromAccountId;
    @NonNull
    private Long toAccountId;
    @NonNull
    @Positive
    private BigDecimal amount;
    private String description;
}
