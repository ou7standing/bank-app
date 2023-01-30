package com.adrian.bank.account;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrencyValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyConstraint {
    String message() default "We only work with USD/EUR/BGN currently";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}