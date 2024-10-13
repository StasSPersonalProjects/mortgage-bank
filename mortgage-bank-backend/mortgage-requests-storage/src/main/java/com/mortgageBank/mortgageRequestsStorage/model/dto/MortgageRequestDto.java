package com.mortgageBank.mortgageRequestsStorage.model.dto;

import com.mortgageBank.mortgageRequestsStorage.model.enums.MortgageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MortgageRequestDto {

    private long id;
    private LocalDate creationDate;
    private List<CustomerDto> borrowers;
    private List<CustomerDto> guarantees;
    private RealEstatePropertyDto realEstatePropertyDto;
    private MortgageStructureDto mortgageStructureDto;
    private MortgageStatus mortgageStatus;

}
