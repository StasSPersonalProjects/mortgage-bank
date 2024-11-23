package com.mortgageBank.mortgageRequestsStorage.model.dto;

import com.mortgageBank.mortgageRequestsStorage.model.documents.MortgageRequest;
import com.mortgageBank.mortgageRequestsStorage.model.enums.MortgageStatus;
import com.mortgageBank.mortgageRequestsStorage.model.enums.QueueType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MortgageRequestDto {

    private long id;
    private LocalDateTime creationTime;
    private String owner;
    private List<CustomerDto> borrowers;
    private List<CustomerDto> guarantees;
    private List<RealEstatePropertyDto> realEstateProperties;
    private MortgageStatus mortgageStatus;
    private String customerDocumentsDirectory;
    private boolean isPulled;
    private String pulledBy;
    private Set<DecisionDto> decisions;
    private LocalDateTime transferTime;
    private QueueType queueType;

    public static MortgageRequestDto of(MortgageRequest request) {
        return MortgageRequestDto
                .builder()
                .id(request.getId())
                .creationTime(request.getCreationTime())
                .owner(request.getOwner())
                .borrowers(request
                        .getBorrowers()
                        .stream()
                        .map(CustomerDto::of)
                        .toList())
                .guarantees(request
                        .getGuarantees()
                        .stream()
                        .map(CustomerDto::of)
                        .toList())
                .realEstateProperties(request
                        .getRealEstateProperties()
                        .stream()
                        .map(RealEstatePropertyDto::of)
                        .toList())
                .mortgageStatus(request.getMortgageStatus())
                .customerDocumentsDirectory(request.getCustomerDocumentsDirectory())
                .isPulled(request.isPulled())
                .pulledBy(request.getPulledBy())
                .decisions(request
                        .getDecisions()
                        .stream()
                        .map(DecisionDto::of)
                        .collect(Collectors.toSet()))
                .transferTime(request.getTransferTime())
                .queueType(request.getQueueType())
                .build();
    }
}
