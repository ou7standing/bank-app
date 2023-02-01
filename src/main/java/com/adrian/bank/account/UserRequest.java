package com.adrian.bank.account;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.math.BigDecimal;

// TODO: 2/1/2023 tova ne ti tryabwa
//@Validated
@Data
public class UserRequest {

    private long id;

    @NotNull(message = "'balance' must not be null / blank")
    // TODO: 2/1/2023 tova mislya che se izpolzwa za drugo
//    @NumberFormat(style = NumberFormat.Style.PERCENT)
    private BigDecimal balance;

    @NotNull(message = "must not be null")
//    @NotEmpty(message = "name must not be empty or null")
    private String name;

    @CurrencyConstraint(message = "use bgn/eur/usd pls")
    @NotNull(message = "enum must not be null")
// TODO: 2/1/2023  ediniya variant e da go naprawish da ne e Currency a String i da slojish tova @Pattern(regexp = "USD|BGN")
// TODO: 2/1/2023 drugiya variant e taya statiya, ti pochti si go napravil https://www.baeldung.com/javax-validations-enums 
    Currency currency;
//    @Pattern(regexp = "USD|BGN|EUR")
//    String currency;
}