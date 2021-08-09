package com.task.linkaja.controller;

import com.task.linkaja.model.request.TransferBalance;
import com.task.linkaja.model.request.InputCustomerRequest;
import com.task.linkaja.service.ITransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<?> getCustomerSaldo(@PathVariable Long account_number) {
        try {
            return transactionService.getCustomerSaldo(account_number);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //create new Customer
    @PostMapping("/addCustomer")
    @ResponseBody
    public ResponseEntity<?> createCustomer(@Valid @RequestBody InputCustomerRequest inputCustomerRequest) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            return new ResponseEntity<>(transactionService.createCustomer(inputCustomerRequest), headers, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //create new Customer
    @PostMapping("/{from_account_number}/transfer")
    public ResponseEntity<?> transferBalance(@PathVariable Long from_account_number,
                                                                   @Valid @RequestBody TransferBalance transferBalance) {
        try {
            return transactionService.transferBalanceService(from_account_number, transferBalance);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
