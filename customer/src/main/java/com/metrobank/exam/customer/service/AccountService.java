package com.metrobank.exam.customer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metrobank.exam.customer.dto.CustomerRequest;
import com.metrobank.exam.customer.dto.AccountInfo;
import com.metrobank.exam.customer.dto.CreatedAccountResponse;
import com.metrobank.exam.customer.dto.CustomerAccountResponse;
import com.metrobank.exam.customer.model.Customer;
import com.metrobank.exam.customer.model.Account;
import com.metrobank.exam.customer.repository.AccountRepository;
import com.metrobank.exam.customer.repository.CustomerRepository;

@Service
public class AccountService {
	@Autowired
    private CustomerRepository customerRepository;
	
	@Autowired
    private AccountRepository accountRepository;

    public CreatedAccountResponse createCustomerAccount(CustomerRequest request) {
        if (request.getCustomerEmail().isBlank()) {
            throw new IllegalArgumentException("Email is a required field");
        }

        Customer customer = new Customer();
        customer.setCustomerName(request.getCustomerName());
        customer.setCustomerMobile(request.getCustomerMobile());
        customer.setCustomerEmail(request.getCustomerEmail());
        customer.setAddress1(request.getAddress1());
        customer.setAddress2(request.getAddress2());

        customer = customerRepository.save(customer);

        CreatedAccountResponse response = new CreatedAccountResponse();
        response.setCustomerNumber(customer.getCustomerNumber());
        response.setTransactionStatusCode(201);
        response.setTransactionStatusDescription("Customer account created");

        return response;
    }

    public CustomerAccountResponse getCustomerAccount(Long customerNumber) {
        Customer customer = accountRepository.findById(customerNumber)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerAccountResponse response = new CustomerAccountResponse();
        response.setCustomerNumber(customer.getCustomerNumber());
        response.setCustomerName(customer.getCustomerName());
        response.setCustomerMobile(customer.getCustomerMobile());
        response.setCustomerEmail(customer.getCustomerEmail());
        response.setAddress1(customer.getAddress1());
        response.setAddress2(customer.getAddress2());
        response.setTransactionStatusDescription("Customer Account found");
        response.setTransactionStatusCode(302);
        
        
        List<AccountInfo> accountInfos = customer.getAccounts().stream()
                .map(account -> new AccountInfo(
                    account.getAccountNumber(),
                    account.getAccountType(),
                    account.getAvailableBalance()))
                .collect(Collectors.toList());

            response.setSavings(accountInfos);

        return response;
    }
}
