package com.adrian.bank.account;

import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
public class BalanceResponse {
    public BigDecimal balance;
    public Currency currency;
}