package com.task.linkaja.controller;

import com.task.linkaja.model.request.TransferBalance;
import com.task.linkaja.model.response.AccountBalanceResponse;
import com.task.linkaja.model.Customer;
import com.task.linkaja.model.request.InputCustomerRequest;
import com.task.linkaja.model.response.TransferBalanceResponse;
import com.task.linkaja.service.ITransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/account")
public class CustomerController {
    @Autowired
    ITransactionService transactionService;

    //get balance
    @GetMapping("/{account_number}")
    public AccountBalanceResponse getCustomerSaldo(@PathVariable Long account_number) {
        try {
            return transactionService.getCustomerSaldo(account_number).getBody();
        } catch (NoSuchElementException e) {
            return new AccountBalanceResponse();
        }
    }

    //create new Customer
    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody InputCustomerRequest inputCustomerRequest) {
        try {
            return new ResponseEntity<>(transactionService.createCustomer(inputCustomerRequest), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //create new Customer
    @PostMapping("/{from_account_number}/transfer")
    public ResponseEntity<TransferBalanceResponse> transferBalance(@PathVariable Long from_account_number,
                                                                   @Valid @RequestBody TransferBalance transferBalance) {
        try {
            return transactionService.transferBalanceService(from_account_number, transferBalance);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
