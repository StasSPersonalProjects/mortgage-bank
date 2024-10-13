package com.mortgageBank.mortgageRequestsStorage.model.entities;

import com.mortgageBank.mortgageRequestsStorage.model.enums.MortgageStatus;
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
@Table(name = "mortgage_requests")
public class MortgageRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToMany
    @JoinTable(
            name = "mortgage_request_borrowers",
            joinColumns = @JoinColumn(name = "mortgage_request_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> borrowers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "mortgage_request_guarantees",
            joinColumns = @JoinColumn(name = "mortgage_request_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> guarantees = new HashSet<>();

    @Column(name = "income_per_person")
    private double incomePerPerson;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "real_estate_property_id", referencedColumnName = "id")
    private RealEstateProperty realEstateProperty;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mortgage_composition_id", referencedColumnName = "id")
    private MortgageComposition mortgageComposition;

    @Enumerated(EnumType.STRING)
    @Column(name = "mortgage_status")
    private MortgageStatus mortgageStatus;

}
