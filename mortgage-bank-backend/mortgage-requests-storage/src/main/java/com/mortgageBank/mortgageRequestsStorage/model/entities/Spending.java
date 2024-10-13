package com.mortgageBank.mortgageRequestsStorage.model.entities;

import com.mortgageBank.mortgageRequestsStorage.model.enums.SpendingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer_spendings")
public class Spending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private SpendingType spendingType;

    private int spendingAmount;

    private int remainingDurationInMonths;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
