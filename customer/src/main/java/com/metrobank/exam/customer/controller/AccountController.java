package com.metrobank.exam.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrobank.exam.customer.dto.CustomerRequest;
import com.metrobank.exam.customer.dto.CreatedAccountResponse;
import com.metrobank.exam.customer.dto.CustomerAccountResponse;
import com.metrobank.exam.customer.dto.ErrorResponse;
import com.metrobank.exam.customer.service.AccountService;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CustomerRequest request) {
        try {
        	CreatedAccountResponse response = accountService.createCustomerAccount(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, e.getMessage()));
        }
    }

    @GetMapping("/{customerNumber}")
    public ResponseEntity<?> getAccount(@PathVariable("customerNumber") String customerNumber) {
        try {
            CustomerAccountResponse response = accountService.getCustomerAccount(Long.parseLong(customerNumber));
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(401, "Customer not found"));
        }
    }
}
