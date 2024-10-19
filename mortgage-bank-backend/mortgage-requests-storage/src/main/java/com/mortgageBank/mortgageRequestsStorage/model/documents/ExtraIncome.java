package com.mortgageBank.mortgageRequestsStorage.model.documents;

import com.mortgageBank.mortgageRequestsStorage.model.dto.ExtraIncomeDto;
import com.mortgageBank.mortgageRequestsStorage.model.enums.IncomeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtraIncome {

    private IncomeType incomeType;
    private String description;
    private int amount;

    public static ExtraIncome of(ExtraIncomeDto dto) {
        return ExtraIncome
                .builder()
                .incomeType(dto.getIncomeType())
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .build();
    }
}
