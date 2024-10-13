package com.mortgageBank.mortgageRequestsStorage.repositories;

import com.mortgageBank.mortgageRequestsStorage.model.entities.RealEstateProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealEstatePropertiesRepository extends JpaRepository<RealEstateProperty, Long> {
}
