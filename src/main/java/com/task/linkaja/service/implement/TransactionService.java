package com.task.linkaja.service.implement;

import com.task.linkaja.model.Account;
import com.task.linkaja.model.Customer;
import com.task.linkaja.model.request.InputCustomerRequest;
import com.task.linkaja.model.request.TransferBalance;
import com.task.linkaja.model.response.AccountBalanceResponse;
import com.task.linkaja.model.response.TransferBalanceResponse;
import com.task.linkaja.repositories.AccountRepository;
import com.task.linkaja.repositories.CustomerRepository;
import com.task.linkaja.service.ITransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class TransactionService implements ITransactionService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public ResponseEntity<AccountBalanceResponse> getCustomerSaldo(Long account_number) {
        log.info("Account Number : " + account_number);
        Account account;
        try{
            account = accountRepository.findByAccountNumber(account_number).get(0);
        }catch (IndexOutOfBoundsException e){
            log.error("Account Number Not Found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Customer> customer = customerRepository.findByCustomerNumber(account.getCustomerNumber());
        AccountBalanceResponse balanceResponse = new AccountBalanceResponse();
        balanceResponse.setBalance(account.getBalance());
        balanceResponse.setAccount_number(account.getAccountNumber());
        balanceResponse.setCustomer_name(customer.get(0).getName());
        return new ResponseEntity<>(balanceResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransferBalanceResponse> transferBalanceService(Long from_account_number, TransferBalance transferBalance) {
        Long to_account_number = transferBalance.getTo_account_number();
        TransferBalanceResponse balanceResponse = new TransferBalanceResponse();
        if(to_account_number.equals(from_account_number)){
            balanceResponse.setMessage("from_account_number is the same as to_account_number");
            return new ResponseEntity<>(balanceResponse, HttpStatus.BAD_REQUEST);
        }
        // check existing From Account
        Account fromAccount;
        try{
            fromAccount = accountRepository.findByAccountNumber(from_account_number).get(0);
        }catch (IndexOutOfBoundsException e){
            log.error("Account Number : "+ from_account_number +" Not Found");
            balanceResponse.setMessage("Account Number : "+ from_account_number +" Not Found");
            log.error(e.getLocalizedMessage());
            return new ResponseEntity<>(balanceResponse, HttpStatus.NOT_FOUND);
        }

        // check existing to Account
        Account toAccount;
        try{
            toAccount = accountRepository.findByAccountNumber(transferBalance.getTo_account_number()).get(0);
        }catch (IndexOutOfBoundsException e){
            log.error("Account Number : "+ to_account_number +" Not Found");
            balanceResponse.setMessage("Account Number : "+ to_account_number +" Not Found");
            log.error(e.getLocalizedMessage());
            return new ResponseEntity<>(balanceResponse, HttpStatus.NOT_FOUND);
        }

        // check from Balance less than 0
        long fromBalance = fromAccount.getBalance() - transferBalance.getAmount();
        if(fromBalance < 0){
            log.error("Balance AC "+ from_account_number +" less than 0");
            balanceResponse.setMessage("Balance AC "+ from_account_number +" less than 0");
            return new ResponseEntity<>(balanceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Long toBalance = toAccount.getBalance() + transferBalance.getAmount();

        fromAccount.setBalance(fromBalance);
        toAccount.setBalance(toBalance);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public Customer createCustomer(InputCustomerRequest inputCustomerRequest) {
        Customer customer = new Customer();
        Account account = new Account();
        customer.setName(inputCustomerRequest.getName());
        customer.setCustomerNumber(inputCustomerRequest.getCustomer_number());

        account.setBalance(inputCustomerRequest.getBalance());
        account.setCustomerNumber(inputCustomerRequest.getCustomer_number());
        account.setAccountNumber(inputCustomerRequest.getAccount_number());
        accountRepository.save(account);
        return customerRepository.save(customer);
    }
}
