package com.mortgageBank.mortgageRequestsStorage.model.dto;

import com.mortgageBank.mortgageRequestsStorage.model.documents.Decision;
import com.mortgageBank.mortgageRequestsStorage.model.enums.DecisionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DecisionDto {

    private DecisionType decisionType;
    private LocalDateTime timestamp;
    private String madeBy;
    private String message;

    public static DecisionDto of(Decision decision) {
        return DecisionDto
                .builder()
                .decisionType(decision.getDecisionType())
                .timestamp(decision.getTimestamp())
                .madeBy(decision.getMadeBy())
                .message(decision.getMessage())
                .build();
    }
}
