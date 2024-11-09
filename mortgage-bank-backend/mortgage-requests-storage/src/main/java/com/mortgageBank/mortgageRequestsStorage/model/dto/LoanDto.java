package com.mortgageBank.mortgageRequestsStorage.model.dto;

import com.mortgageBank.mortgageRequestsStorage.model.documents.Loan;
import com.mortgageBank.mortgageRequestsStorage.model.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanDto {

    private LoanType loanType;
    private int loanAmount;
    private int durationInMonths;
    private double annualInterestRate;

    public static LoanDto of(Loan loan) {
        return LoanDto
                .builder()
                .loanType(loan.getLoanType())
                .loanAmount(loan.getLoanAmount())
                .durationInMonths(loan.getDurationInMonths())
                .annualInterestRate(loan.getAnnualInterestRate())
                .build();
    }
}
