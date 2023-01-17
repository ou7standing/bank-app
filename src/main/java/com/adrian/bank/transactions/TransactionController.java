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
    public BalanceResponse deposit(long id, BigDecimal depositSum) throws Exception {
        return transactionService.deposit (id, depositSum);
    }

    @PostMapping("/transactions/withdrawal")
    public BalanceResponse withdrawal(long id, BigDecimal withdrawalSum) throws Exception {
        return transactionService.withdrawal (id, withdrawalSum);
    }

    @PostMapping("transactions/transfer")
    public BalanceResponse transfer(long idFromAcc, long idToAcc, BigDecimal transferSum) throws Exception {
        return transactionService.transferFunds (idFromAcc, idToAcc, transferSum);
    }


    @PostMapping("transactions/exchange")
    public BalanceResponse exchangeCurrency(BigDecimal amount, long fromAccount, long toAccount) throws Exception {
        return transactionService.exchangeCurrency (amount, fromAccount, toAccount);
    }

    @GetMapping("transactions/check-transaction")
    public Transaction checkTransaction(long transID) throws Exception {
        return transactionService.checkTransaction (transID);
    }


    @PostMapping("transactions/check-status")
    public TransactionStatus checkTransStatus(long transactionID) throws Exception {
        return transactionService.checkStatus (transactionID);
    }


//    @PostMapping("transactions/make-transaction")
//    public Transaction createTransaction(long accountId, TransactionType type, Currency fromCurrency,
//                                         Currency toCurrency, BigDecimal sumRequest, BigDecimal exchangeRate) {
//        return transactionService.createTransaction (accountId, type, fromCurrency, toCurrency, sumRequest, exchangeRate);
//    }


}
