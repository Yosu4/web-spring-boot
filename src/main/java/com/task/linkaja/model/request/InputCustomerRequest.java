package com.task.linkaja.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InputCustomerRequest {
    private Long account_number;

    private int customer_number;

    private Long balance;

    private String name;
}
