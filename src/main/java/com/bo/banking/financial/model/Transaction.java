package com.bo.banking.financial.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Transaction implements Serializable {
    private Long id;
    private BigDecimal amount;
    private String description;
    private Account fromAccount;
    private Account toAccount;
    private LocalDateTime date;
}
