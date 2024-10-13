package com.mortgageBank.mortgageMarginsConfig.repositories;

import com.mortgageBank.mortgageMarginsConfig.model.entities.LoanRateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MarginsConfigRepository extends JpaRepository<LoanRateType, Integer> {

    @Modifying
    @Query(value = "DELETE FROM loan_types WHERE loan_Type = :name", nativeQuery = true)
    void deleteByName(@Param(value = "name") String name);

    @Query(value = "SELECT * FROM loan_types WHERE loan_type = :loanType", nativeQuery = true)
    Optional<LoanRateType> findByLoanRateType(@Param("loanType") String loanRateType);
}
