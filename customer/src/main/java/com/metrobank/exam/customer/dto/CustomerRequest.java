package com.metrobank.exam.customer.dto;

import com.metrobank.exam.customer.model.AccountType;

public class CustomerRequest {

	
	private String customerName;
    private String customerMobile;
    private String customerEmail;
    private String address1;
    private String address2;
    private AccountType accountType;
    
    public CustomerRequest() {
	}
    
    public CustomerRequest(String customerName, String customerMobile, String customerEmail, String address1,
			String address2, AccountType accountType) {
		super();
		this.customerName = customerName;
		this.customerMobile = customerMobile;
		this.customerEmail = customerEmail;
		this.address1 = address1;
		this.address2 = address2;
		this.accountType = accountType;
	}
    
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
}
