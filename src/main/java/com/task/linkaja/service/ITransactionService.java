package com.task.linkaja.service;

import com.task.linkaja.model.Customer;
import com.task.linkaja.model.request.InputCustomerRequest;
import com.task.linkaja.model.request.TransferBalance;
import com.task.linkaja.model.response.AccountBalanceResponse;
import com.task.linkaja.model.response.TransferBalanceResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITransactionService {
    public ResponseEntity<AccountBalanceResponse> getCustomerSaldo(Long account_number);

    public ResponseEntity<TransferBalanceResponse> transferBalanceService(Long from_account_number, TransferBalance transferBalance);

    public Customer createCustomer(InputCustomerRequest inputCustomerRequest);
}
