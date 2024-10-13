package com.mortgageBank.mortgageRequestsStorage.model.entities;

import com.mortgageBank.mortgageRequestsStorage.model.enums.EmploymentType;
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
@Table(name = "customers_employment_data")
public class CustomersEmploymentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private EmploymentType employmentType;
    private String placeOfEmployment;
    private String position;
    private double durationOfEmploymentInYears;
    private int monthlySalary;
    private int yearlySalary;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}
