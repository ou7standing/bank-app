package com.adrian.bank.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {


    @Autowired
    public AccountRepository accountRepository;


//    TransactionService ts = new TransactionService ();

    public Account createAccount(String name, BigDecimal balance, Currency currency) {
        Account newAccount = new Account (balance, name, currency);
        accountRepository.save (newAccount);

//        ts.createTransaction (newAccount.getId (), TransactionType.accountCreation,
//                currency,currency,balance,BigDecimal.valueOf (1));

        return newAccount;
    }


    public BalanceResponse getBalance(long id) throws Exception {
        Account account = findAccount (id);
        return new BalanceResponse (account.getBalance (), account.getCurrency ());
    }


    public Account findAccount(long id) throws Exception {
        return accountRepository.findById (id).orElseThrow (() -> new Exception ("There's no such id"));
    }


    public Account changeDetails(long id, String newName) throws Exception {
        Account account = findAccount (id);
        account.setOwnerName (newName);
        accountRepository.save (account);
        return account;
    }

    public String deleteAccount(long id) throws Exception {
        Account account = findAccount (id);

//        ts.createTransaction (id, TransactionType.accountDeletion,
//                account.getCurrency (),account.getCurrency (),account.getBalance (), BigDecimal.valueOf (1));

        accountRepository.delete (account);
        return ("Account# " + id + " deleted successfully!");
    }
}
