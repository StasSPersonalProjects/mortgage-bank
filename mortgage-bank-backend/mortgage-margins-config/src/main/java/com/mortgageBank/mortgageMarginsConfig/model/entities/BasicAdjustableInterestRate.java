package com.mortgageBank.mortgageMarginsConfig.model.entities;

import com.mortgageBank.mortgageMarginsConfig.model.dto.AdjustableRateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "basic_adjustable_interest_rates")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicAdjustableInterestRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String loanType;
    private double basicInterestRate;

    public static BasicAdjustableInterestRate of(AdjustableRateDto dto) {
        return BasicAdjustableInterestRate
                .builder()
                .loanType(dto.getLoanRateType().name())
                .basicInterestRate(dto.getBasicInterestRate())
                .build();
    }

}
