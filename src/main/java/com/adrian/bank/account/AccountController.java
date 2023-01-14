package com.adrian.bank.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/account")
    public Account makeAccount(String name, BigDecimal balance, Currency currency) {
        return accountService.createAccount (name, balance, currency);
    }

    @GetMapping("/account/balance")
    public BalanceResponse showBalance(long id) throws Exception {
        return accountService.getBalance (id);
    }

    //trea se izolira sluchaq v koito vkarvame 1 i sushto ime 
    @PutMapping("/account/change-details")
    public Account changeDetails(long id, String newName) throws Exception {
        return accountService.changeDetails (id, newName);
    }

    @DeleteMapping("/account/delete")
    public String deleteAccount(long id) throws Exception {
        return accountService.deleteAccount (id);
    }

    @PostMapping("/account/deposit")
    public BalanceResponse deposit(long id, BigDecimal depositSum) throws Exception {
        return accountService.deposit (id, depositSum);
    }

    @PostMapping("/account/withdrawal")
    public BalanceResponse withdrawal(long id, BigDecimal withdrawalsum) throws Exception {
        return accountService.withdrawal (id, withdrawalsum);
    }

    @PostMapping("account/transfer")
    public BalanceResponse transfer(long idFromAcc, long idToAcc, BigDecimal transferSum) throws Exception {
        return accountService.transferFunds (idFromAcc, idToAcc, transferSum);
    }



}

