package com.adrian.bank.account;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Validated
@Data
public class UserRequest {

    private long id;

    @NotNull(message = "'balance' must not be null / blank")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private BigDecimal balance;

    @NotNull(message = "must not be null")
    @NotEmpty(message = "'name' field must not be empty")
    private String name;

    @CurrencyConstraint(message = "use bgn/eur/usd pls")
    @NotNull(message = "enum must not be null")
    Currency currency;
}