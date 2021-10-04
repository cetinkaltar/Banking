package com.bo.banking.customer.service;

import com.bo.banking.customer.dao.CustomerDao;
import com.bo.banking.customer.dto.CustomerRequest;
import com.bo.banking.customer.model.Customer;
import com.bo.banking.customer.service.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

import static com.bo.banking.utils.RandomUtils.randomCustomer;
import static com.bo.banking.utils.RandomUtils.randomLong;
import static com.bo.banking.utils.RandomUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerDao customerDao;
    @Mock
    private CustomerMapper customerMapper;
    @InjectMocks
    private CustomerService customerService;

    @Test
    public void findById() {
        //given
        Customer customer = new Customer();
        customer.setId(randomLong());
        when(customerDao.findById(customer.getId())).thenReturn(Optional.of(customer));

        //when
        customerService.findById(customer.getId());

        //then
        verify(customerMapper).mapToDto(customer);
    }

    @Test
    void findEntityById() {
        //given
        Customer customer = new Customer();
        customer.setId(randomLong());
        when(customerDao.findById(customer.getId())).thenReturn(Optional.of(customer));

        //when
        Customer result = customerService.findEntityById(customer.getId());

        //then
        assertThat(result.getId()).isEqualTo(customer.getId());
    }

    @Test
    void add() {
        //given
        CustomerRequest request = mock(CustomerRequest.class);
        Customer customer = randomCustomer();

        when(customerMapper.map(request)).thenReturn(customer);

        //when
        customerService.add(request);

        //then
        verify(customerDao).add(customer);
    }

    @Test
    void update() {
        //given
        CustomerRequest request = mock(CustomerRequest.class);
        Customer customer = randomCustomer();
        when(customerDao.findById(customer.getId())).thenReturn(Optional.of(customer));

        //when
        customerService.update(customer.getId(), request);

        //then
        verify(customerDao).findById(customer.getId());
        verify(customerDao).update(customer);
    }

    @Test
    void delete() {
        //given
        Customer customer = new Customer();
        customer.setId(randomLong());
        when(customerDao.findById(customer.getId())).thenReturn(Optional.of(customer));

        //when
        customerService.delete(customer.getId());

        //then
        verify(customerDao).findById(customer.getId());
        verify(customerDao).delete(customer.getId());
    }

    @Test
    void search() {
        //given
        Customer customer = new Customer();
        customer.setId(randomLong());
        customer.setFirstName(randomString());
        when(customerDao.search(customer.getFirstName().toUpperCase(Locale.ROOT))).thenReturn(Collections.singletonList(customer));

        //when
        customerService.search(customer.getFirstName());

        //then
        verify(customerMapper, times(1)).mapToDto(customer);
    }
}