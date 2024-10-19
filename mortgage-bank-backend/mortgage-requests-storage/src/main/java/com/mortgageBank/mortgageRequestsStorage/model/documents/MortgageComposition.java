package com.mortgageBank.mortgageRequestsStorage.model.documents;

import com.mortgageBank.mortgageRequestsStorage.model.dto.MortgageCompositionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MortgageComposition {

    private List<Loan> loans;
    private int totalAmount;

    public static MortgageComposition of(MortgageCompositionDto dto) {
        return MortgageComposition
                .builder()
                .loans(dto.getLoans().stream().map(Loan::of).toList())
                .totalAmount(dto.getTotalAmount())
                .build();
    }
}
