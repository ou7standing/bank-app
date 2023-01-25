package com.adrian.bank.transactions;

import com.adrian.bank.account.Currency;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic
    private java.sql.Timestamp dateTime;

    @Column
    private long accountId;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private Currency fromCurrency;

    @Enumerated(EnumType.STRING)
    private Currency toCurrency;

    @Column
    private BigDecimal sumRequest;

    @Column
    private BigDecimal exchangeRate;

    @Column
    private BigDecimal finalSum;

    @Setter(AccessLevel.PACKAGE)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;


    public Transaction(long accountId, TransactionType type, Currency fromCurrency, Currency toCurrency, BigDecimal sumRequest, BigDecimal exchangeRate) {
        this.accountId = accountId;
        this.dateTime = Timestamp.valueOf (LocalDateTime.now ());
        this.type = type;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.sumRequest = sumRequest;
        this.exchangeRate = exchangeRate;
        this.finalSum = sumRequest.multiply (exchangeRate);
    }

}





