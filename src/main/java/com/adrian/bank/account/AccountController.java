package com.adrian.bank.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
@Validated
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping
    public Account makeAccount(String name, BigDecimal balance, Currency currency) {
        return accountService.createAccount (name, balance, currency);
    }

    @GetMapping("/balance")
    public BalanceResponse showBalance(long id) {
        return accountService.getBalance (id);
    }

    @PutMapping("/change-details")
    public Account changeDetails(long id, String newName) {
        return accountService.changeDetails (id, newName);
    }

    @DeleteMapping("/delete")
    public String deleteAccount(long id) {
        return accountService.deleteAccount (id);
    }


}

