package com.mortgageBank.authService.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsraeliIdValidator implements ConstraintValidator<ValidIsraeliId, String> {

    @Override
    public void initialize(ValidIsraeliId constraintAnnotation) {
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        if (id == null || id.length() != 9 || !id.matches("\\d+")) {
            return false; // Check for null, length, and numeric only
        }

        int sum = 0;
        for (int i = 0; i < id.length(); i++) {
            int digit = Character.getNumericValue(id.charAt(i));
            digit *= (i % 2) + 1;
            if (digit > 9) {
                digit -= 9;
            }
            sum += digit;
        }
        return sum % 10 == 0;
    }
}
