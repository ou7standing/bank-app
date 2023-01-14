package com.adrian.bank.transactions;

import com.adrian.bank.account.Account;
import com.adrian.bank.account.AccountRepository;
import com.adrian.bank.account.BalanceResponse;
import com.adrian.bank.account.Currency;
import com.adrian.bank.exchange.ExchangeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
@Log4j2
public class TransactionService {


    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    public ExchangeService exchangeService;

    @Autowired
    public TransactionRepository transactionRepository;


    public Account findAccount(long id) throws Exception {
        return accountRepository.findById (id).orElseThrow (() -> new Exception ("There's no such id"));
    }

    public Transaction createTransaction(long accountId, TransactionType type, Currency fromCurrency,
                                         Currency toCurrency, BigDecimal transferSum,
                                         BigDecimal exchangeRate) {
        Transaction transaction = new Transaction (accountId, type, fromCurrency, toCurrency, transferSum,
                exchangeRate);
        transactionRepository.save (transaction);
        return transaction;
    }


    public BalanceResponse deposit(long id, BigDecimal depositAmount) throws Exception {
        Account account = findAccount (id);
        account.deposit (depositAmount);
        accountRepository.save (account);

        createTransaction (id, TransactionType.deposit, findAccount (id).getCurrency (), findAccount (id).getCurrency ()
                , depositAmount, BigDecimal.valueOf (1));

        return new BalanceResponse (account.getBalance (), account.getCurrency ());
    }


    public BalanceResponse withdrawal(long id, BigDecimal withdrawalAmount) throws Exception {
        Account account = findAccount (id);
        account.withdrawal (withdrawalAmount);
        accountRepository.save (account);

        createTransaction (id, TransactionType.withdrawal, findAccount (id).getCurrency (), findAccount (id).getCurrency ()
                , withdrawalAmount, BigDecimal.valueOf (1));

        return new BalanceResponse (account.getBalance (), account.getCurrency ());
    }

    public BalanceResponse transferFunds(long idFromAcc, long idToAcc, BigDecimal transferSum) throws Exception {
        if (!findAccount (idFromAcc).getCurrency ().equals (findAccount (idToAcc).getCurrency ())) {
            throw new Exception ("Account id " + idFromAcc + " has different currency from account# " + idToAcc);
        } else {
            withdrawal (idFromAcc, transferSum);

            createTransaction (idFromAcc, TransactionType.transfer, findAccount (idFromAcc).getCurrency (), findAccount (idToAcc).getCurrency ()
                    , transferSum, BigDecimal.valueOf (1));

            return deposit (idToAcc, transferSum);
        }
    }


    public BalanceResponse exchangeCurrency(BigDecimal amount, long fromAccount, long toAccount) throws Exception {

        Account giveTo = findAccount (toAccount);

        Account takeFrom = findAccount (fromAccount);

        // tva tree se pogledne, shtoto ako ima nqkakva greshka po-nadolu, shte wzeme edni pari samo
        withdrawal (fromAccount, amount);

        BigDecimal rate = exchangeService.getRate (takeFrom.getCurrency (), giveTo.getCurrency ()).setScale (4, RoundingMode.HALF_UP);

        log.info (String.valueOf (rate));

        BigDecimal depositAmount = amount.multiply (rate).setScale (2, RoundingMode.HALF_UP);

        log.info (String.valueOf (depositAmount));

        deposit (toAccount, depositAmount);

        Transaction transaction = createTransaction (fromAccount, TransactionType.exchangeCurrency, takeFrom.getCurrency (), giveTo.getCurrency ()
                , amount, rate);
        long transID = transaction.getTransID ();
        transactionRepository.deleteById (transID - 2);
        transactionRepository.deleteById (transID - 1);

        return new BalanceResponse (giveTo.getBalance (), giveTo.getCurrency ());

    }


}


