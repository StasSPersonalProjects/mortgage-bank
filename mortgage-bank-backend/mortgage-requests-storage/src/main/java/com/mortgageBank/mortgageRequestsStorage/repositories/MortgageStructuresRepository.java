package com.mortgageBank.mortgageRequestsStorage.repositories;

import com.mortgageBank.mortgageRequestsStorage.model.entities.MortgageComposition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MortgageStructuresRepository extends JpaRepository<MortgageComposition, Long> {
}
