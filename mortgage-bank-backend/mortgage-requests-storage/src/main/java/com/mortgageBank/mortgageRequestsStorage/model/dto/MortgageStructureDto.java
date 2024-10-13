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
public class MortgageStructureDto {

    private List<LoanDto> loansList;
    private int totalAmount;
}
