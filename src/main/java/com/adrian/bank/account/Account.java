package com.adrian.bank.account;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "account")
public class Account {
    @Id
    @SequenceGenerator(name = "mysequence", initialValue = 7)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="mysequence")
    private long id;

    @Column
    private BigDecimal balance;

    @Column
    private String ownerName;
    @Enumerated(EnumType.STRING)
    Currency currency;

    public Account() {
    }

    public void setOwnerName(String ownerName) throws Exception {
        if (ownerName.equals (this.ownerName)) {
            throw new Exception ("Owner name is already " + ownerName);
        }
        this.ownerName = ownerName;
    }

    public Account(BigDecimal balance, String ownerName, Currency currency) {
        this.balance = balance;
        this.ownerName = ownerName;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getId() {
        return id;
    }

    public BigDecimal deposit(BigDecimal depositSum) {
        balance = balance.add (depositSum);
        return balance;
    }

    public BigDecimal withdrawal(BigDecimal withdrawalSum) throws Exception {
        if (withdrawalSum.compareTo (balance) == 1) {
            throw new Exception ("No enough funds");
        }
        balance = balance.subtract (withdrawalSum);
        System.out.println (withdrawalSum + "has been deducted from the balance and the new balance is "
                + balance);
        return balance;
    }


}
