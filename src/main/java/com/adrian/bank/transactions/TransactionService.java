package com.adrian.bank.transactions;

import com.adrian.bank.account.Account;
import com.adrian.bank.account.AccountService;
import com.adrian.bank.account.BalanceResponse;
import com.adrian.bank.account.Currency;
import com.adrian.bank.exception.EntityNotFoundExc;
import com.adrian.bank.exception.SpringBootExc;
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
    public AccountService accountService;

    @Autowired
    public ExchangeService exchangeService;

    @Autowired
    public TransactionRepository transactionRepository;

    public Transaction createTransaction(long accountId, TransactionType type, Currency fromCurrency,
                                         Currency toCurrency, BigDecimal transferSum, BigDecimal exchangeRate) {
        Transaction transaction = new Transaction (accountId, type, fromCurrency, toCurrency, transferSum, exchangeRate);
        transaction.setStatus (TransactionStatus.SUCCESSFUL);
        transactionRepository.save (transaction);
        return transaction;
    }


    public BalanceResponse deposit(long id, BigDecimal depositAmount) {
        Account account = accountService.findAccount (id);
        account.deposit (depositAmount);
        accountService.accountRepository.save (account);

        createTransaction (id, TransactionType.DEPOSIT, account.getCurrency (), account.getCurrency (),
                depositAmount, BigDecimal.valueOf (1));

        return new BalanceResponse (account.getBalance (), account.getCurrency ());
    }


    public BalanceResponse withdrawal(long id, BigDecimal withdrawalAmount) {
        Account account = accountService.findAccount (id);
        account.withdrawal (withdrawalAmount);
        accountService.accountRepository.save (account);

        createTransaction (id, TransactionType.WITHDRAWAL, account.getCurrency (), account.getCurrency (),
                withdrawalAmount, BigDecimal.valueOf (1));

        return new BalanceResponse (account.getBalance (), account.getCurrency ());
    }

    public BalanceResponse transferFunds(long idFromAcc, long idToAcc, BigDecimal transferSum) {
        if (!accountService.findAccount (idFromAcc).getCurrency ().equals
                (accountService.findAccount (idToAcc).getCurrency ())) {
            throw new SpringBootExc ("Account id " + idFromAcc + " has different currency from account# " + idToAcc
                    + "please go to Exchange Currency for such operation");
        } else if (idFromAcc == idToAcc) {
            throw new SpringBootExc ("Cannot transfer values between the same account");
        } else {
            withdrawal (idFromAcc, transferSum);

            createTransaction (idFromAcc, TransactionType.TRANSFER, accountService.findAccount (idFromAcc).getCurrency (),
                    accountService.findAccount (idToAcc).getCurrency (), transferSum, BigDecimal.valueOf (1));

            return deposit (idToAcc, transferSum);
        }
    }


    public BalanceResponse exchangeCurrency(BigDecimal amount, long fromAccount, long toAccount) {

        Account giveTo = accountService.findAccount (toAccount);

        Account takeFrom = accountService.findAccount (fromAccount);

        // tva tree se pogledne, shtoto ako ima nqkakva greshka po-nadolu, shte wzeme edni pari samo
        withdrawal (fromAccount, amount);

        BigDecimal rate = exchangeService.getRate (takeFrom.getCurrency (), giveTo.getCurrency ()).setScale (4, RoundingMode.HALF_UP);

        log.info (String.valueOf (rate));

        BigDecimal depositAmount = amount.multiply (rate).setScale (2, RoundingMode.HALF_UP);

        log.info (String.valueOf (depositAmount));

        deposit (toAccount, depositAmount);

        Transaction transaction = createTransaction (fromAccount, TransactionType.EXCHANGE_CURRENCY, takeFrom.getCurrency (), giveTo.getCurrency (), amount, rate);
        long transID = transaction.getId ();

        transactionRepository.deleteById (transID - 2);
        transactionRepository.deleteById (transID - 1);

        return new BalanceResponse (giveTo.getBalance (), giveTo.getCurrency ());

    }

    public Transaction checkTransaction(long id) {
        return transactionRepository.findById (id).orElseThrow (() -> new EntityNotFoundExc ("There's no" +
                " such transaction ID"));
    }

    public TransactionStatus checkStatus(long id) {
        return checkTransaction (id).getStatus ();
    }

}

