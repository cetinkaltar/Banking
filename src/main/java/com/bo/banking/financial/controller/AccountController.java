package com.bo.banking.financial.controller;

import com.bo.banking.financial.dto.AccountRequest;
import com.bo.banking.financial.dto.enums.AccountDto;
import com.bo.banking.financial.service.AccountService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/bo/account")
public class AccountController {
    private final AccountService accountService;

    @ApiOperation(value = "Returns account data by id")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @ApiOperation(value = "Add new account")
    @PostMapping
    public ResponseEntity<AccountDto> add(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.add(request));
    }

    @ApiOperation(value = "Delete account")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        accountService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "Return accounts for given customer")
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<AccountDto>> findAllByCustomerId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.findAllByCustomerId(id));
    }
}
