package com.mortgageBank.mortgageMarginsConfig.model.entities;

import com.mortgageBank.mortgageMarginsConfig.model.dto.LoanRateTypeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "loan_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRateType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "loan_type")
    private String loanType;

    @ElementCollection
    @CollectionTable(name = "rate_margins", joinColumns = @JoinColumn(name = "loan_type_id"))
    @MapKeyColumn(name = "range_in_months")
    @Column(name = "zero_margin_value")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<String, Double> zeroMarginRates = new HashMap<>();

    public static LoanRateType of(LoanRateTypeDto dto) {
        return LoanRateType
                .builder()
                .loanType(dto.getLoanType().name())
                .zeroMarginRates(dto.getZeroMarginRates())
                .build();
    }
}
