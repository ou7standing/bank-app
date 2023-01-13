package com.adrian.bank.account;

public class BalanceResponse {

    public double balance;
    public Currency currency;


    public BalanceResponse(double balance, Currency currency) {
        this.balance = balance;
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
