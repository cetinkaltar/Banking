package com.bo.banking.customer.service.mapper;

import com.bo.banking.customer.dto.CustomerDto;
import com.bo.banking.customer.dto.CustomerRequest;
import com.bo.banking.customer.model.Customer;
import com.bo.banking.financial.service.mapper.AccountMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static com.bo.banking.utils.RandomUtils.randomAccount;
import static com.bo.banking.utils.RandomUtils.randomCustomer;
import static com.bo.banking.utils.RandomUtils.randomCustomerRequest;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomerMapperTest {
    @Mock
    private AccountMapper accountMapper;
    @InjectMocks
    private CustomerMapperImpl mapper;

    @Test
    void mapToDto() {
        //given
        Customer customer = randomCustomer();
        customer.setAccounts(Collections.singletonList(randomAccount()));

        //when
        CustomerDto result = mapper.mapToDto(customer);

        //then
        assertThat(result.getId()).isEqualTo(customer.getId());
        assertThat(result.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(result.getLastName()).isEqualTo(customer.getLastName());
        assertThat(result.getAccounts().size()).isEqualTo(customer.getAccounts().size());
    }

    @Test
    void update() {
        //given
        Customer customer = randomCustomer();
        CustomerRequest request = randomCustomerRequest();

        //when
        mapper.update(customer, request);

        //then
        assertThat(customer.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(customer.getLastName()).isEqualTo(request.getLastName());
    }

    @Test
    void map() {
        //given
        CustomerRequest request = randomCustomerRequest();

        //when
        Customer result = mapper.map(request);

        //then
        assertThat(result.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(result.getLastName()).isEqualTo(request.getLastName());
    }
}