package com.task.linkaja.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.linkaja.model.Account;
import com.task.linkaja.model.Customer;
import com.task.linkaja.model.request.TransferBalance;
import com.task.linkaja.model.response.AccountBalanceResponse;
import com.task.linkaja.repositories.AccountRepository;
import com.task.linkaja.repositories.CustomerRepository;
import com.task.linkaja.service.ITransactionService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.BDDAssumptions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
//@RunWith(SpringRunner.class)
//@WebMvcTest(CustomerController.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ITransactionService transactionService;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    AccountRepository accountRepository;

    @Test
    void getCustomerSaldo() throws Exception{
        Account account = new Account(1L, 555010L, 1001, 10000l);
        List<Account> accounts = Arrays.asList(account);
        Mockito.when(accountRepository.findByAccountNumber(555010L)).thenReturn(accounts);
        //BDDMockito.given(this.accountRepository.findByAccountNumber(555010L)).willReturn(accounts);

        Customer customer = new Customer(1L, 1001, "Kurniawan");
        List<Customer> customers = Arrays.asList(customer);
        Mockito.when(customerRepository.findByCustomerNumber(1001)).thenReturn(customers);
        //BDDMockito.given(this.customerRepository.findByCustomerNumber(1001)).willReturn(customers);

        mockMvc.perform( MockMvcRequestBuilders
                .get("/account/{account_number}", 555010L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

//        mockMvc.perform(MockMvcRequestBuilders.get("/account/555010")
//                    .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.balance").value("15000"));

        //assertEquals("Kurniawan", customerRepository.getById(1L).getName());
        //AccountBalanceResponse response = parseResponse

//                .andExpect(MockMvcResultMatchers.content()
//                        .json("{'account_number': 555010, 'customer_name' : 'Kurniawan', 'balance':1}"));

//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.body.customer_name", Matchers.is("Kurniawan")));
//        RequestBuilder request = MockMvcRequestBuilders.get("/account/555010").accept(MediaType.APPLICATION_JSON_VALUE);
//        MvcResult result = mockMvc.perform(request).andReturn();
//        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void transferBalance() throws Exception {
        TransferBalance transferBalance = new TransferBalance();
        transferBalance.setAmount(1000L);
        transferBalance.setTo_account_number(555013L);
        String inputJson = mapToJson(transferBalance);
        RequestBuilder request = MockMvcRequestBuilders.post("/account/555012/transfer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson);
        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}