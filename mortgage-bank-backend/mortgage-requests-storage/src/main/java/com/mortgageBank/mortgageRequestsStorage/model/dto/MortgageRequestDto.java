package com.mortgageBank.mortgageRequestsStorage.model.dto;

import com.mortgageBank.mortgageRequestsStorage.model.documents.MortgageRequest;
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
    private String owner;
    private List<CustomerDto> borrowers;
    private List<CustomerDto> guarantees;
    private RealEstatePropertyDto realEstateProperty;
    private MortgageCompositionDto mortgageComposition;
    private MortgageStatus mortgageStatus;
    private String customerDocumentsDirectory;
    private boolean isPulled;
    private String pulledBy;
    private DecisionDto decision;

    public static MortgageRequestDto of(MortgageRequest request) {
        return MortgageRequestDto
                .builder()
                .id(request.getId())
                .creationDate(request.getCreationDate())
                // TODO
                .build();
    }
}
