package com.bo.banking.customer.controller;

import com.bo.banking.customer.dto.CustomerDto;
import com.bo.banking.customer.dto.CustomerRequest;
import com.bo.banking.customer.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/bo/customer")
public class CustomerController {
    private final CustomerService customerService;

    @ApiOperation(value = "Returns customer data according to customer id")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @ApiOperation(value = "Add new customer")
    @PostMapping
    public ResponseEntity<CustomerDto> add(@RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.add(request));
    }

    @ApiOperation(value = "Update customer")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@PathVariable("id") Long id, @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.update(id, request));
    }

    @ApiOperation(value = "Delete customer")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        customerService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "Returns customer data according to search paramater for first name , last name")
    @GetMapping("/filter")
    public ResponseEntity<List<CustomerDto>> search(@RequestParam(value = "searchQuery", required = false) String searchQuery) {
        return ResponseEntity.ok(customerService.search(searchQuery));
    }
}
