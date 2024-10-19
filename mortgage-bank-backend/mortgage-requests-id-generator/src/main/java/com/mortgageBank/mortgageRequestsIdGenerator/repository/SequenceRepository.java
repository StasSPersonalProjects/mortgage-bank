package com.mortgageBank.mortgageRequestsIdGenerator.repository;

import com.mortgageBank.mortgageRequestsIdGenerator.model.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SequenceRepository extends MongoRepository<Sequence, String> {
}
