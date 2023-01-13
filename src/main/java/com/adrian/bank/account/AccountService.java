package com.adrian.bank.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {


    @Autowired
    public AccountRepository accountRepository;


    public Account createAccount(String name, double balance, Currency currency) {
        Account newAccount = new Account (balance, name, currency);
        accountRepository.save (newAccount);
        return newAccount;
    }


    public BalanceResponse getBalance(long id) throws Exception {
        Account account = findAccount (id);
        return new BalanceResponse (account.getBalance (), account.getCurrency ());
    }


    public BalanceResponse deposit(long id, double depositAmount) throws Exception {
        Account account = findAccount (id);
        account.deposit (depositAmount);
        accountRepository.save (account);
        return new BalanceResponse (account.getBalance (), account.getCurrency ());
    }


    public BalanceResponse withdrawal(long id, double withdrawalAmount) throws Exception {
        Account account = findAccount (id);
        account.withdrawal (withdrawalAmount);
        accountRepository.save (account);
        return new BalanceResponse (account.getBalance (), account.getCurrency ());
    }

    public Account findAccount(long id) throws Exception {
        return accountRepository.findById (id).orElseThrow (() -> new Exception ("There's no such id"));

        //        return accounts.stream ().filter (account -> account.getId () == id).findAny ().orElseThrow (() -> new Exception ("There's no such id"));
    }


    public Account changeDetails(long id, String newName) throws Exception {
        Account account = findAccount (id);
        account.setOwnerName (newName);
        accountRepository.save (account);
        return account;
    }

    public String deleteAccount(long id) throws Exception {
        Account account = findAccount (id);
        accountRepository.delete (account);
//        accounts.remove (account);
        return ("Account# " + id + " deleted successfully!");
    }

    public BalanceResponse transferFunds(long idFromAcc, long idToAcc, double transferSum) throws Exception {
        if (!findAccount (idFromAcc).getCurrency ().equals (findAccount (idToAcc).getCurrency ())) {
            throw new Exception ("Account id " + idFromAcc + " has different currency from account# " + idToAcc);
        } else {
            withdrawal (idFromAcc, transferSum);
            return deposit (idToAcc, transferSum);
        }


    }

}
