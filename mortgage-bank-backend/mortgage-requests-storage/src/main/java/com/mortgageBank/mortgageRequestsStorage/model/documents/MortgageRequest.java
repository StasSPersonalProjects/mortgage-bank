package com.mortgageBank.mortgageRequestsStorage.model.documents;

import com.mortgageBank.mortgageRequestsStorage.model.dto.MortgageRequestDto;
import com.mortgageBank.mortgageRequestsStorage.model.enums.MortgageStatus;
import com.mortgageBank.mortgageRequestsStorage.model.enums.QueueType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "mortgage_requests")
public class MortgageRequest {

    @Id
    private long id;
    private LocalDateTime creationTime;
    private String owner;
    private Set<Customer> borrowers;
    private Set<Customer> guarantees;
    private List<RealEstateProperty> realEstateProperties;
    private MortgageStatus mortgageStatus;
    private String customerDocumentsDirectory;
    private boolean isPulled;
    private String pulledBy;
    private Set<Decision> decisions;
    private LocalDateTime transferTime;
    private QueueType queueType;

    public static MortgageRequest of(MortgageRequestDto dto) {
        return MortgageRequest
                .builder()
                .creationTime(dto.getCreationTime())
                .owner(dto.getOwner())
                .borrowers(dto.getBorrowers()
                        .stream()
                        .map(Customer::of)
                        .collect(Collectors.toSet()))
                .guarantees(dto.getGuarantees().stream()
                        .map(Customer::of)
                        .collect(Collectors.toSet()))
                .realEstateProperties(dto
                        .getRealEstateProperties()
                        .stream()
                        .map(RealEstateProperty::of)
                        .toList())
                .mortgageStatus(MortgageStatus.IN_PROGRESS)
                .customerDocumentsDirectory(null)
                .isPulled(false)
                .pulledBy(null)
                .decisions(dto
                        .getDecisions()
                        .stream()
                        .map(Decision::of)
                        .collect(Collectors.toSet()))
                .transferTime(dto.getTransferTime())
                .queueType(QueueType.INITIAL)
                .build();
    }
}