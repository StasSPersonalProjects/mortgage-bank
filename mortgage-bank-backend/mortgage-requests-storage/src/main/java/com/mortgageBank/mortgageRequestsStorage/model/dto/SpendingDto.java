package com.mortgageBank.mortgageRequestsStorage.model.dto;

import com.mortgageBank.mortgageRequestsStorage.model.documents.Spending;
import com.mortgageBank.mortgageRequestsStorage.model.enums.SpendingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpendingDto {

    private SpendingType spendingType;
    private int spendingAmount;
    private int remainingDurationInMonths;

    public static SpendingDto of(Spending spending) {
        return SpendingDto
                .builder()
                .spendingType(spending.getSpendingType())
                .spendingAmount(spending.getSpendingAmount())
                .remainingDurationInMonths(spending.getRemainingDurationInMonths())
                .build();
    }
}
