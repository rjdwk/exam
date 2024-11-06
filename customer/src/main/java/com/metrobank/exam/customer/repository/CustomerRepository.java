package com.metrobank.exam.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metrobank.exam.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
