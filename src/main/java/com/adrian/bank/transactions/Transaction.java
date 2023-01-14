package com.adrian.bank.transactions;

import com.adrian.bank.account.Currency;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transID;

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

    public Transaction() {
    }

    public Transaction(long accountId, TransactionType type, Currency fromCurrency, Currency toCurrency, BigDecimal sumRequest, BigDecimal exchangeRate) {
        this.accountId = accountId;
        this.dateTime = Timestamp.valueOf (LocalDateTime.now());
        this.type = type;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.sumRequest = sumRequest;
        this.exchangeRate = exchangeRate;
        this.finalSum = sumRequest.multiply (exchangeRate);
    }

    public long getTransID() {
        return transID;
    }

    public void setTransID(long transID) {
        this.transID = transID;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(java.sql.Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal getSumRequest() {
        return sumRequest;
    }

    public void setSumRequest(BigDecimal sumRequest) {
        this.sumRequest = sumRequest;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getFinalSum() {
        return finalSum;
    }

    public void setFinalSum(BigDecimal finalSum) {
        this.finalSum = finalSum;
    }


}





