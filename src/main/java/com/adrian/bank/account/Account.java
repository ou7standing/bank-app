package com.adrian.bank.account;

import com.adrian.bank.exception.SpringBootExc;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @SequenceGenerator(name = "mySequence", initialValue = 7)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "mySequence")
    private long id;

    @Column
    private BigDecimal balance;

    @Column
    private String ownerName;

    @Enumerated(EnumType.STRING)
    Currency currency;

    public Account(BigDecimal balance, String ownerName, Currency currency) {
        this.balance = balance;
        this.ownerName = ownerName;
        this.currency = currency;
    }

    public void setOwnerName(String ownerName) {
        if (ownerName.equals (this.ownerName))
            throw new SpringBootExc ("Owner name is already " + ownerName);
        this.ownerName = ownerName;
    }
}