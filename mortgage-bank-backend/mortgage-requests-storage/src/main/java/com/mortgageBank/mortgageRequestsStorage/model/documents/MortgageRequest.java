package com.mortgageBank.mortgageRequestsStorage.model.documents;

import com.mortgageBank.mortgageRequestsStorage.model.dto.MortgageRequestDto;
import com.mortgageBank.mortgageRequestsStorage.model.enums.MortgageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
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
    private LocalDate creationDate;
    private String owner;
    private Set<Customer> borrowers;
    private Set<Customer> guarantees;
    private RealEstateProperty realEstateProperty;
    private MortgageComposition mortgageComposition;
    private MortgageStatus mortgageStatus;
    private String customerDocumentsDirectory;
    private boolean isPulled;
    private String pulledBy;
    private Decision decision;

    public static MortgageRequest of(MortgageRequestDto dto) {
        return MortgageRequest
                .builder()
                .creationDate(dto.getCreationDate())
                .owner(null)
                .borrowers(dto.getBorrowers()
                        .stream()
                        .map(Customer::of)
                        .collect(Collectors.toSet()))
                .guarantees(dto.getGuarantees().stream()
                        .map(Customer::of)
                        .collect(Collectors.toSet()))
                .realEstateProperty(RealEstateProperty.of(dto.getRealEstateProperty()))
                .mortgageComposition(MortgageComposition.of(dto.getMortgageComposition()))
                .mortgageStatus(MortgageStatus.IN_PROGRESS)
                .customerDocumentsDirectory(null)
                .isPulled(false)
                .pulledBy(null)
                .decision(null)
                .build();
    }
}