package com.adrian.bank.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public Account makeAccount(@Valid UserRequest userRequest) {
        return accountService.createAccount (userRequest.getName (), userRequest.getBalance (),
                Currency.valueOf (userRequest.getCurrency ().toUpperCase ()));
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