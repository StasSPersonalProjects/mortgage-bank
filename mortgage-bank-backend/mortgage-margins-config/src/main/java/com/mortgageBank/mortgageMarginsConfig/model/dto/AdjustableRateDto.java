package com.mortgageBank.mortgageMarginsConfig.model.dto;

import com.mortgageBank.mortgageMarginsConfig.model.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdjustableRateDto {

    private LoanType loanRateType;
    private double basicInterestRate;
}
