package com.bo.banking.financial.dto.enums;

import com.bo.banking.financial.validator.ValidTransactionRequest;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FilterRequest {
    @NonNull
    private Long accountId;
    @NonNull
    LocalDate dayFrom;
    @NonNull
    LocalDate dayTo;
}
