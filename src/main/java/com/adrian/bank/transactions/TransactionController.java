package com.adrian.bank.transactions;

import com.adrian.bank.account.BalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @PostMapping("/account/deposit")
    public BalanceResponse deposit(long id, BigDecimal depositSum) throws Exception {
        return transactionService.deposit (id, depositSum);
    }

    @PostMapping("/account/withdrawal")
    public BalanceResponse withdrawal(long id, BigDecimal withdrawalsum) throws Exception {
        return transactionService.withdrawal (id, withdrawalsum);
    }

    @PostMapping("account/transfer")
    public BalanceResponse transfer(long idFromAcc, long idToAcc, BigDecimal transferSum) throws Exception {
        return transactionService.transferFunds (idFromAcc, idToAcc, transferSum);
    }


    @PostMapping("account/exchange")
    public BalanceResponse exchangeCurrency(BigDecimal amount, long fromAccount, long toAccount) throws Exception {
        return transactionService.exchangeCurrency (amount, fromAccount, toAccount);
    }


}
