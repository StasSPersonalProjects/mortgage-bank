package com.mortgageBank.mortgageRequestsStorage.repositories;

import com.mortgageBank.mortgageRequestsStorage.model.entities.CustomersEmploymentData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersEmploymentDataRepository extends JpaRepository<CustomersEmploymentData, Long> {
}
