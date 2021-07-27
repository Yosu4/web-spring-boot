package com.task.linkaja.repositories;

import com.task.linkaja.model.Account;
import com.task.linkaja.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByCustomerNumber(int customerNumber);
}
