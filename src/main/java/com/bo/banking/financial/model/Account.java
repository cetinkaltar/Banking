package com.bo.banking.financial.model;

import com.bo.banking.customer.model.Customer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Account implements Serializable {
    private Long id;
    private String iban;
    private BigDecimal balance;
    private List<Transaction> transactions;
    private Customer customer;
}
