package com.adrian.bank.account;

import java.math.BigDecimal;

public class BalanceResponse {

    public BigDecimal balance;
    public Currency currency;


    public BalanceResponse(BigDecimal balance, Currency currency) {
        this.balance = balance;
        this.currency = currency;
    }

}
