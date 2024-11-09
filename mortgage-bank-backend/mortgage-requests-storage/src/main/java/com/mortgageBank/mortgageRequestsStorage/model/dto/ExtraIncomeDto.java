package com.mortgageBank.mortgageRequestsStorage.model.dto;

import com.mortgageBank.mortgageRequestsStorage.model.documents.ExtraIncome;
import com.mortgageBank.mortgageRequestsStorage.model.enums.IncomeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtraIncomeDto {

    private IncomeType incomeType;
    private int amount;
    private String description;

    public static ExtraIncomeDto of(ExtraIncome extraIncome) {
        return ExtraIncomeDto
                .builder()
                .incomeType(extraIncome.getIncomeType())
                .description(extraIncome.getDescription())
                .amount(extraIncome.getAmount())
                .build();
    }
}
