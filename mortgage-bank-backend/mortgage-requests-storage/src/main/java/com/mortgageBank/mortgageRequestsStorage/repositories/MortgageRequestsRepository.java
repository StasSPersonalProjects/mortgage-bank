package com.mortgageBank.mortgageRequestsStorage.repositories;

import com.mortgageBank.mortgageRequestsStorage.model.entities.MortgageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MortgageRequestsRepository extends JpaRepository<MortgageRequest, Long> {

}
