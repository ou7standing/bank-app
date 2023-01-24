package com.adrian.bank.transactions;

import com.adrian.bank.account.BalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController

public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @PostMapping("/transactions/deposit")
    public BalanceResponse deposit(long id, BigDecimal depositSum) {
        return transactionService.deposit (id, depositSum);
    }

    @PostMapping("/transactions/withdrawal")
    public BalanceResponse withdrawal(long id, BigDecimal withdrawalSum) {
        return transactionService.withdrawal (id, withdrawalSum);
    }

    @PostMapping("transactions/transfer")
    public BalanceResponse transfer(long idFromAcc, long idToAcc, BigDecimal transferSum) {
        return transactionService.transferFunds (idFromAcc, idToAcc, transferSum);
    }


    @PostMapping("transactions/exchange")
    public BalanceResponse exchangeCurrency(BigDecimal amount, long fromAccount, long toAccount) {
        return transactionService.exchangeCurrency (amount, fromAccount, toAccount);
    }

    @GetMapping("transactions/check-transaction")
    public Transaction checkTransaction(long transID) {
        return transactionService.checkTransaction (transID);
    }


    @PostMapping("transactions/check-status")
    public TransactionStatus checkTransStatus(long transactionID) {
        return transactionService.checkStatus (transactionID);
    }


}
