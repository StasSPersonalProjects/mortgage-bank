package com.mortgageBank.mortgageRequestsStorage.model.documents;

import com.mortgageBank.mortgageRequestsStorage.model.dto.DecisionDto;
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
public class Decision {

    private DecisionType decisionType;
    private LocalDateTime timestamp;
    private String madeBy;
    private String message;

    public static Decision of(DecisionDto dto) {
        return Decision
                .builder()
                .decisionType(dto.getDecisionType())
                .timestamp(dto.getTimestamp())
                .madeBy(dto.getMadeBy())
                .message(dto.getMessage())
                .build();
    }
}
