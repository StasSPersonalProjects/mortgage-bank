package com.mortgageBank.mortgageRequestsStorage.model.dto;

import com.mortgageBank.mortgageRequestsStorage.model.documents.MortgageComposition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MortgageCompositionDto {

    private List<LoanDto> loans;
    private int totalAmount;

    public static MortgageCompositionDto of(MortgageComposition composition) {
        return MortgageCompositionDto
                .builder()
                .loans(composition.getLoans().stream().map(LoanDto::of).toList())
                .totalAmount(composition.getTotalAmount())
                .build();
    }
}
