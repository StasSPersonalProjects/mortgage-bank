package com.mortgageBank.mortgageRequestsStorage.repositories;

import com.mortgageBank.mortgageRequestsStorage.model.documents.MortgageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MortgageRequestsRepository extends MongoRepository<MortgageRequest, Long> {

}
