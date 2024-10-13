package com.mortgageBank.mortgageRequestsStorage.repositories;

import com.mortgageBank.mortgageRequestsStorage.model.entities.Spending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSpendingsRepository extends JpaRepository<Spending, Long> {
}
