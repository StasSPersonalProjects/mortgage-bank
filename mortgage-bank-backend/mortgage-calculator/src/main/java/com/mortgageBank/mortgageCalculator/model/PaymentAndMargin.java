package com.mortgageBank.mortgageCalculator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentAndMargin {

    private double payment;
    private double marginInPercent;
    private double marginInCurrency;

}
