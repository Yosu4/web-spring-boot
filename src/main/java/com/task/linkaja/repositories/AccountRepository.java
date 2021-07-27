package com.task.linkaja.repositories;

import com.task.linkaja.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
   List<Account> findByAccountNumber(Long accountNumber);
}
