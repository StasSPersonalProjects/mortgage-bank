package com.mortgageBank.mortgageCalculator.model;

import com.mortgageBank.mortgageCalculator.model.enums.LoanType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRequestDto {

    private LoanType loanType;

    @Positive(message = "Loan amount must be a positive number")
    private int loanAmount;

    @Positive(message = "Interest rate must be a positive number")
    private double annualInterestRate;

    @Positive(message = "Duration rate must be a positive number")
    @Min(value = 6, message = "The duration must be between 6 and 360 months")
    @Max(value = 360, message = "The duration must be between 6 and 360 months")
    private int loanTermInMonths;

}
