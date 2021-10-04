package com.bo.banking.customer.service;

import com.bo.banking.customer.dao.CustomerDao;
import com.bo.banking.customer.dto.CustomerDto;
import com.bo.banking.customer.dto.CustomerRequest;
import com.bo.banking.customer.model.Customer;
import com.bo.banking.customer.service.mapper.CustomerMapper;
import com.bo.banking.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerDao customerDao;
    private final CustomerMapper customerMapper;

    public CustomerDto findById(Long id) {
        return customerMapper.mapToDto(customerDao.findById(id).orElseThrow(() -> new NotFoundException(Customer.class, id)));
    }

    public Customer findEntityById(Long id) {
        return customerDao.findById(id).orElseThrow(() -> new NotFoundException(Customer.class, id));
    }

    public CustomerDto add(CustomerRequest request) {
        Customer customer = customerMapper.map(request);
        customerDao.add(customer);

        return customerMapper.mapToDto(customer);
    }

    public CustomerDto update(Long id, CustomerRequest request) {
        Customer customer = findEntityById(id);
        customerMapper.update(customer, request);
        customerDao.update(customer);

        return customerMapper.mapToDto(customer);
    }

    public void delete(Long id) {
        findEntityById(id);
        customerDao.delete(id);
    }

    public List<CustomerDto> search(String searchQuery) {
        return customerDao.search(toUpperCaseIfNotNull(searchQuery)).stream()
                .map(customerMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private String toUpperCaseIfNotNull(String string) {
        return string != null ? string.toUpperCase() : "";
    }
}
