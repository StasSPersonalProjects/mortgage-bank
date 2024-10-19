package com.mortgageBank.mortgageRequestsStorage.model.documents;

import com.mortgageBank.mortgageRequestsStorage.model.dto.SpendingDto;
import com.mortgageBank.mortgageRequestsStorage.model.enums.SpendingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Spending {

    private SpendingType spendingType;
    private int spendingAmount;
    private int remainingDurationInMonths;

    public static Spending of(SpendingDto dto) {
        return Spending
                .builder()
                .spendingType(dto.getSpendingType())
                .spendingAmount(dto.getSpendingAmount())
                .remainingDurationInMonths(dto.getRemainingDurationInMonths())
                .build();
    }
}
