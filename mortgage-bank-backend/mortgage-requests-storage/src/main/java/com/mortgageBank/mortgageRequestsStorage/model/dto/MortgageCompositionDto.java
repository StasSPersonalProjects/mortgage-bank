package com.mortgageBank.mortgageRequestsStorage.model.dto;

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
}
