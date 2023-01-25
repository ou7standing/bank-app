package com.adrian.bank.account;

import com.adrian.bank.exception.EntityNotFoundExc;
import com.adrian.bank.transactions.TransactionService;
import com.adrian.bank.transactions.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    public TransactionService transService;

    public Account createAccount(String name, BigDecimal balance, Currency currency) {
        Account newAccount = new Account (balance, name, currency);
        accountRepository.save (newAccount);

        transService.createTransaction (newAccount.getId (), TransactionType.DEPOSIT,
                currency, currency, balance, BigDecimal.valueOf (1));

        return newAccount;
    }

    public BalanceResponse getBalance(long id) {
        Account account = findAccount (id);
        return new BalanceResponse (account.getBalance (), account.getCurrency ());
    }

    public Account findAccount(long id) {
        return accountRepository.findById (id).orElseThrow (() -> new EntityNotFoundExc ("Account " + id +
                " not found."));
    }

    public Account changeDetails(long id, String newName) {
        Account account = findAccount (id);
        account.setOwnerName (newName);
        accountRepository.save (account);
        return account;
    }

    public String deleteAccount(long id) {
        Account account = findAccount (id);
        transService.createTransaction (id, TransactionType.WITHDRAWAL,
                account.getCurrency (), account.getCurrency (), account.getBalance (), BigDecimal.valueOf (1));
        accountRepository.delete (account);
        return ("Account# " + id + " deleted successfully!");
    }
}