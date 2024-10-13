package com.mortgageBank.mortgageMarginsConfig.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableLoanTypesDto {

    private int id;
    private String loanType;
}
