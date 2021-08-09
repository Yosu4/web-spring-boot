package com.task.linkaja.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.linkaja.model.Account;
import com.task.linkaja.model.Customer;
import com.task.linkaja.model.request.InputCustomerRequest;
import com.task.linkaja.model.request.TransferBalance;
import com.task.linkaja.repositories.AccountRepository;
import com.task.linkaja.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    AccountRepository accountRepository;

    @Test
    @DisplayName("GET /Saldo Customer Kurniawan")
    void getSaldoAgain() throws Exception {
        Account account = new Account(1L, 555011L, 1002, 10000L);
        List<Account> accounts = Arrays.asList(account);
        doReturn(accounts).when(accountRepository).findByAccountNumber(555011L);

        Customer customer = new Customer(1L, 1002, "Kurniawann");
        List<Customer> customers = Arrays.asList(customer);
        doReturn(customers).when(customerRepository).findByCustomerNumber(1002);

        mockMvc.perform(get("/account/{account_number}", 555011L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer_name").value("Kurniawann"));
    }

    @Test
    @DisplayName("Post /Transfer from 555020L to 555021L")
    void transferBalance() throws Exception {
        TransferBalance transferBalance = new TransferBalance();
        transferBalance.setAmount(1000L);
        transferBalance.setTo_account_number(555021L);

        Account account1 = new Account(3L, 555020L, 1003, 20000L);
        List<Account> account1s = Arrays.asList(account1);
        doReturn(account1s).when(accountRepository).findByAccountNumber(555020L);

        Account account2 = new Account(4L, 555021L, 1004, 10000L);
        List<Account> account2s = Arrays.asList(account2);
        doReturn(account2s).when(accountRepository).findByAccountNumber(555021L);

        mockMvc.perform(post("/account/{from_account_number}/transfer", 555020L)
                .contentType(MediaType.APPLICATION_JSON)
        .content(mapToJson(transferBalance)))
        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Add customer data")
    void createCustomer() throws Exception{
        Account account = new Account(1L, 555050L, 1005, 10000L);
        //doReturn(account).when(accountRepository).save(account);

        Customer customer = new Customer(1L, 1005, "Kurniawann");
        //doReturn(customer).when(customerRepository).save(customer);

        when(accountRepository.save(any())).thenReturn(account);
        when(customerRepository.save(any())).thenReturn(customer);

        InputCustomerRequest inputCustomerRequest = new InputCustomerRequest(555050L,
                1005, 10000L, "Kurniawann");
        mockMvc.perform(post("/account/addCustomer")
                .content(mapToJson(new InputCustomerRequest(555050L,
                        1005, 10000L, "Kurniawann")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerNumber").value(1005));
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}