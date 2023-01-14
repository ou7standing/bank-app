package com.adrian.bank.account;

import java.math.BigDecimal;

public class BalanceResponse {

    public BigDecimal balance;
    public Currency currency;


    public BalanceResponse(BigDecimal balance, Currency currency) {
        this.balance = balance;
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
