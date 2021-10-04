package com.bo.banking.financial.dto;

import com.bo.banking.financial.dto.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDto extends TransactionRequest {
    private Long id;
    private LocalDateTime date;
    private PaymentType type;
}
