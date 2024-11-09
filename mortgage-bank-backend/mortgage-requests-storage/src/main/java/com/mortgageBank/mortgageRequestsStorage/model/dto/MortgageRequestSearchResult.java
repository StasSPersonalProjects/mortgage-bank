package com.mortgageBank.mortgageRequestsStorage.model.dto;

import com.mortgageBank.mortgageRequestsStorage.model.documents.MortgageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MortgageRequestSearchResult {

    private long id;
    private String creationTime;
    private String location;
    private String mortgageStatus;

    public static MortgageRequestSearchResult of(MortgageRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return MortgageRequestSearchResult
                .builder()
                .id(request.getId())
                .creationTime(request.getCreationTime().format(formatter))
                .location(request.getRealEstateProperty().getLocation())
                .mortgageStatus(request.getMortgageStatus().name())
                .build();
    }
}
