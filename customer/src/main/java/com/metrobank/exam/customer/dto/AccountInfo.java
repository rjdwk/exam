package com.metrobank.exam.customer.dto;

import com.metrobank.exam.customer.model.AccountType;

public class AccountInfo {
    private Long accountNumber;
    private AccountType accountType;
    private double availableBalance;

    public AccountInfo(Long accountNumber, AccountType accountType, double availableBalance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.availableBalance = availableBalance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }
}
