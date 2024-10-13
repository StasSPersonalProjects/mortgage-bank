package com.mortgageBank.mortgageCalculator.utils.calculator;

import org.springframework.stereotype.Component;

@Component
public class MonthlyPaymentCalculator {

    public double calculateMonthlyPayment(
            int loanAmount,
            double annualInterestRate,
            int loanTermInMonths) {
        double monthlyInterestRate = (annualInterestRate / 100) / 12;
        return loanAmount *
                (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTermInMonths)) /
                (Math.pow(1 + monthlyInterestRate, loanTermInMonths) - 1);
    }
}
