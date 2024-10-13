package com.mortgageBank.mortgageRequestsStorage.model.entities;

import com.mortgageBank.mortgageRequestsStorage.model.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "identity_card_number")
    private long identityCardNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "children_under_age_21")
    private int childrenUnderAge21;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<CustomersEmploymentData> employmentData;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Spending> spendings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<ExtraIncome> extraIncomes;

    @Column(name = "extra_info", length = 1024)
    private String extraInfo;

    @ManyToMany(mappedBy = "borrowers")
    private Set<MortgageRequest> mortgageRequestsAsBorrower = new HashSet<>();

    @ManyToMany(mappedBy = "guarantees")
    private Set<MortgageRequest> mortgageRequestsAsGuarantee = new HashSet<>();

}
