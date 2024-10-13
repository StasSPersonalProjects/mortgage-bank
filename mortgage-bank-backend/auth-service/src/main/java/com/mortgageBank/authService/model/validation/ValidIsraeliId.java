package com.mortgageBank.authService.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsraeliIdValidator.class)
public @interface ValidIsraeliId {

    String message() default "Invalid Israeli ID number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
