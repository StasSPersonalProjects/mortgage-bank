package com.mortgageBank.mortgageRequestsStorage.model.documents;

import com.mortgageBank.mortgageRequestsStorage.model.dto.LoanDto;
import com.mortgageBank.mortgageRequestsStorage.model.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loan {

    private LoanType loanType;
    private int loanAmount;
    private int durationInMonths;
    private double annualInterestRate;

    public static Loan of(LoanDto dto) {
        return Loan
                .builder()
                .loanType(dto.getLoanType())
                .loanAmount(dto.getLoanAmount())
                .durationInMonths(dto.getDurationInMonths())
                .annualInterestRate(dto.getAnnualInterestRate())
                .build();
    }
}
