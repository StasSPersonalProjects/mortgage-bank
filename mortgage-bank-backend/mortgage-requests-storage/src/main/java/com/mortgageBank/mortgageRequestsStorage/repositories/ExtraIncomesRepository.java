package com.mortgageBank.mortgageRequestsStorage.repositories;

import com.mortgageBank.mortgageRequestsStorage.model.entities.ExtraIncome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtraIncomesRepository extends JpaRepository<ExtraIncome, Long> {
}
