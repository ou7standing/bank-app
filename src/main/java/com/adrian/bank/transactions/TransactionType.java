package com.adrian.bank.transactions;

public enum TransactionType {
    // TODO: 1/18/2023 ne e nujno, deposit raboti po sushtiya nachin
    accountCreation,

    deposit,
    // TODO: 1/18/2023 FAILED; enum stoinostite se pishat celite v uppercase
    withdrawal,

    transfer,

    exchangeCurrency,

    // TODO: 1/18/2023 ne e nujno, withdral raboti po sushtiya nachin
    accountDeletion;
}
