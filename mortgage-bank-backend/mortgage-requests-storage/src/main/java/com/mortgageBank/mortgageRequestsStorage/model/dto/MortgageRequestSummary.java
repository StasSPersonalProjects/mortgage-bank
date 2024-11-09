package com.mortgageBank.mortgageRequestsStorage.model.dto;

import com.mortgageBank.mortgageRequestsStorage.model.documents.Customer;
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
public class MortgageRequestSummary {

    private long id;
    private String borrowersLastName;
    private String transferTime;
    private String owner;

    public static MortgageRequestSummary of(MortgageRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return MortgageRequestSummary
                .builder()
                .id(request.getId())
                .borrowersLastName(
                        request.getBorrowers()
                                .stream()
                                .findFirst()
                                .map(Customer::getLastName)
                                .orElse(null)
                )
                .transferTime(request.getTransferTime().format(formatter))
                .owner(request.getOwner())
                .build();
    }
}
