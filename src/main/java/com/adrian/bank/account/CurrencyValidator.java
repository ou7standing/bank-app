package com.adrian.bank.account;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CurrencyValidator implements ConstraintValidator<CurrencyConstraint, Currency> {

    @Override
    public void initialize(CurrencyConstraint currency) {
    }

    @Override
    public boolean isValid(Currency currency, ConstraintValidatorContext constraintValidatorContext) {
        if (currency == null) {
            return false;
        }
        switch (currency.name ()) {
            case "EUR":
            case "USD":
            case "BGN":
                return true;
        }
        return false;
    }
}
