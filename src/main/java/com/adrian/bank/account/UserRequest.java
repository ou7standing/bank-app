package com.adrian.bank.account;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class UserRequest {

    private long id;

    @NotNull(message = "'balance' must not be null / blank")
    private BigDecimal balance;

    @Pattern(regexp = "[a-zA-Z ]+", message = "'name' must contain only characters and spaces")
    @NotBlank(message = "'name' must not be blank")
    private String name;

    @Pattern(regexp = "USD|BGN|EUR|usd|bgn|eur", message = "Please use USD/BGN/EUR as a currency.")
    String currency;
}