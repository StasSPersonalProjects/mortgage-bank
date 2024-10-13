package com.mortgageBank.mortgageMarginsConfig.model.dto;

import com.mortgageBank.mortgageMarginsConfig.model.enums.LoanType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRateTypeDto {

    private LoanType loanType;
    @NotNull(message = "please fill the values")
    private Map<String, Double> zeroMarginRates = new HashMap<>();
}
