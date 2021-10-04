package com.bo.banking.financial.controller;

import com.bo.banking.financial.dto.TransactionDto;
import com.bo.banking.financial.dto.TransactionRequest;
import com.bo.banking.financial.dto.enums.FilterRequest;
import com.bo.banking.financial.service.TransactionService;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/bo/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @ApiOperation(value = "Return account transactions by filter date")
    @PostMapping("/filter")
    public ResponseEntity<List<TransactionDto>> filter(@RequestBody FilterRequest request) {
        return ResponseEntity.ok(transactionService.filterAccountTransactionsByDate(request));
    }

    @ApiOperation(value = "Add new transaction")
    @PostMapping
    public ResponseEntity<TransactionDto> add(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(transactionService.add(request));
    }

    @ApiOperation(value = "Delete transaction")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        transactionService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "Return account transactions")
    @GetMapping("/account/{id}")
    public ResponseEntity<List<TransactionDto>> getTransactions(@PathVariable("id") Long id) {
        return ResponseEntity.ok(transactionService.getTransactionsByAccountId(id));
    }
}
