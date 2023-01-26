package com.adrian.bank.transactions;

import com.adrian.bank.account.BalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public BalanceResponse deposit(long id, BigDecimal depositSum) {
        return transactionService.deposit (id, depositSum);
    }

    @PostMapping("/withdrawal")
    public BalanceResponse withdrawal(long id, BigDecimal withdrawalSum) {
        return transactionService.withdrawal (id, withdrawalSum);
    }

    @PostMapping("/transfer")
    public BalanceResponse transfer(long idFromAcc, long idToAcc, BigDecimal transferSum) {
        return transactionService.transferFunds (idFromAcc, idToAcc, transferSum);
    }

    @PostMapping("/exchange")
    public BalanceResponse exchangeCurrency(BigDecimal amount, long fromAccount, long toAccount) {
        return transactionService.exchangeCurrency (amount, fromAccount, toAccount);
    }

    @GetMapping("/check-transaction")
    public Transaction checkTransaction(long transID) {
        return transactionService.checkTransaction (transID);
    }

    @PostMapping("/check-status")
    public TransactionStatus checkTransStatus(long transactionID) {
        return transactionService.checkStatus (transactionID);
    }
}