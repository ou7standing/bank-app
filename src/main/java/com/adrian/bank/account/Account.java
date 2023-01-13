package com.adrian.bank.account;

import javax.persistence.*;

@Entity(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private double balance;
    @Column
    private String ownerName;
    @Enumerated(EnumType.STRING)
    Currency currency;

    public Account() {
    }

    public void setOwnerName(String ownerName) throws Exception {
        if(ownerName.equals (this.ownerName)) {
            throw new Exception ("Owner name is already " + ownerName);
        }
        this.ownerName = ownerName;
    }

    public Account(double balance, String ownerName, Currency currency) {
        this.balance = balance;
        this.ownerName = ownerName;
//        this.id = IdGenerator.returnNewID ();  // commented-out 11 Jan
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }


    public double getBalance() {
        return balance;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getId() {
        return id;
    }

    public double deposit(double depositSum) {
        balance = balance + depositSum;
        return balance;
    }

    public double withdrawal(double withdrawalSum) throws Exception {
        if (withdrawalSum > balance) {
            throw new Exception ("No enough funds");
        }
        balance = balance - withdrawalSum;
        System.out.println (withdrawalSum + "has been deducted from the balance and the new balance is "
                + balance);
        return balance;
    }


}
