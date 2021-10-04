package com.bo.banking.customer.model;

import com.bo.banking.financial.model.Account;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Customer implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Account> accounts;
}
