package com.mortgageBank.mortgageMarginsConfig.repositories;

import com.mortgageBank.mortgageMarginsConfig.model.entities.BasicAdjustableInterestRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BasicAdjustableInterestRatesRepository
        extends JpaRepository<BasicAdjustableInterestRate, Integer> {

    Optional<BasicAdjustableInterestRate> findByLoanType(String loanType);

    @Query(value = "SELECT basic_interest_rate FROM basic_adjustable_interest_rates " +
            "WHERE loan_type = :loanType", nativeQuery = true)
    Optional<Double> findBasicInterestRateByLoanType(@Param("loanType") String loanType);
}
