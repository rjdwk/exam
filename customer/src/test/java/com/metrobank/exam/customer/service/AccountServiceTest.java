package com.metrobank.exam.customer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.metrobank.exam.customer.dto.CreatedAccountResponse;
import com.metrobank.exam.customer.dto.CustomerAccountResponse;
import com.metrobank.exam.customer.dto.CustomerRequest;
import com.metrobank.exam.customer.model.Account;
import com.metrobank.exam.customer.model.Customer;
import com.metrobank.exam.customer.model.AccountType;
import com.metrobank.exam.customer.repository.AccountRepository;
import com.metrobank.exam.customer.repository.CustomerRepository;

class AccountServiceTest {

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private AccountRepository accountRepository;

	private AccountService accountService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		accountService = new AccountService();
		ReflectionTestUtils.setField(accountService, "customerRepository", customerRepository);
		ReflectionTestUtils.setField(accountService, "accountRepository", accountRepository);

	}

	@Test
	void testCreateCustomerAccount_success() {
		CustomerRequest request = populateCustomerRequest();
		Customer mockCustomer = new Customer();
		mockCustomer.setCustomerName("John Doe");
		mockCustomer.setCustomerMobile("1234567890");
		mockCustomer.setCustomerEmail("john@example.com");
		mockCustomer.setAddress1("123 Main St");
		mockCustomer.setAddress2("Apt 4B");
		mockCustomer.setCustomerNumber(1L);

		when(customerRepository.save(any(Customer.class))).thenReturn(mockCustomer);

		CreatedAccountResponse response = accountService.createCustomerAccount(request);

		assertNotNull(response);
		assertEquals(201, response.getTransactionStatusCode());
		assertEquals("Customer account created", response.getTransactionStatusDescription());
		assertEquals(1L, response.getCustomerNumber());

		verify(customerRepository).save(any(Customer.class)); // Verify that save was called
	}

	@Test
	void testCreateCustomerAccount_blankEmail() {
		CustomerRequest request = populateInvalidCustomerRequest();

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			accountService.createCustomerAccount(request);
		});

		assertEquals("Email is a required field", exception.getMessage());
		verify(customerRepository, never()).save(any(Customer.class)); // Ensure save is not called
	}

	@Test
	void testGetCustomerAccount_success() {
		Long customerNumber = 1L;
		Customer mockCustomer = new Customer();
		mockCustomer.setCustomerNumber(1L);
		mockCustomer.setCustomerName("John Doe");
		mockCustomer.setCustomerMobile("1234567890");
		mockCustomer.setCustomerEmail("john@example.com");
		mockCustomer.setAddress1("123 Main St");
		mockCustomer.setAddress2("Apt 4B");

		Account mockAccount = new Account();
		mockAccount.setAccountNumber(1001L);
		mockAccount.setAccountType(AccountType.SAVINGS);
		mockAccount.setAvailableBalance(5000.00);

		mockCustomer.setAccounts(List.of(mockAccount));

		when(accountRepository.findById(customerNumber)).thenReturn(Optional.of(mockCustomer));

		// Act
		CustomerAccountResponse response = accountService.getCustomerAccount(customerNumber);

		// Assert
		assertNotNull(response);
		assertEquals(302, response.getTransactionStatusCode());
		assertEquals("Customer Account found", response.getTransactionStatusDescription());
		assertEquals(mockCustomer.getCustomerNumber(), response.getCustomerNumber());
		assertEquals(mockCustomer.getCustomerName(), response.getCustomerName());
		assertEquals(mockCustomer.getCustomerMobile(), response.getCustomerMobile());
		assertEquals(mockCustomer.getCustomerEmail(), response.getCustomerEmail());
		assertEquals(mockCustomer.getAddress1(), response.getAddress1());
		assertEquals(mockCustomer.getAddress2(), response.getAddress2());
		assertEquals(1, response.getSavings().size()); // Ensure we have the correct number of accounts

		verify(accountRepository).findById(customerNumber); // Verify that findById was called
	}

	@Test
	void testGetCustomerAccount_notFound() {
		// Arrange
		Long customerNumber = 1L;
		when(accountRepository.findById(customerNumber)).thenReturn(Optional.empty());

		// Act & Assert
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			accountService.getCustomerAccount(customerNumber);
		});

		assertEquals("Customer not found", exception.getMessage());
		verify(accountRepository).findById(customerNumber);
	}
	
	public CustomerRequest populateCustomerRequest() {
		CustomerRequest request = new CustomerRequest();
		request.setCustomerName("Test");
		request.setCustomerMobile("121321321");
		request.setCustomerEmail("test234asd@gmail.com");
		request.setAddress1("St. City");
		request.setAddress2("Province, Coumtry");
		request.setAccountType(AccountType.SAVINGS);
		return request;
	}
	
	public CustomerRequest populateInvalidCustomerRequest() {
		CustomerRequest request = new CustomerRequest();
		request.setCustomerName("Test");
		request.setCustomerMobile("121321321");
		request.setCustomerEmail("");
		request.setAddress1("St. City");
		request.setAddress2("Province, Coumtry");
		request.setAccountType(AccountType.SAVINGS);
		return request;
	}

}
