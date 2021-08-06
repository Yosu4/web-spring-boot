package com.task.linkaja.controller;

import com.task.linkaja.model.response.AccountBalanceResponse;
import com.task.linkaja.service.implement.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ControllerTestGetSaldo {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;

    // Cara ini jangan di campur dengan unit test yang satunya
    // Karena MockBean transactionService tidak di set di cara yang satunya
    // Sehingga defatultnya null,
    // dan yang di baca adalah transactionService terlebih dahulu dibanding accountRepository dan customerRepository
    // Maka engga bisa sampai kebaca di file Repository
    @Test
    @DisplayName("GET /Saldo Customer Kurniawan 10000")
    void getCustomerSaldo() throws Exception{
        AccountBalanceResponse balanceResponse = new AccountBalanceResponse(555010L, "Kurniawan", 10000l);
        doReturn(new ResponseEntity<>(balanceResponse, HttpStatus.OK)).when(transactionService).getCustomerSaldo(555010L);

        mockMvc.perform(get("/account/{account_number}", 555010L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(10000));
    }
}
