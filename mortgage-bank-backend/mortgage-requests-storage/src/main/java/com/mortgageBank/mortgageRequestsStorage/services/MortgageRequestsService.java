package com.mortgageBank.mortgageRequestsStorage.services;

import com.mortgageBank.mortgageRequestsStorage.model.documents.MortgageRequest;
import com.mortgageBank.mortgageRequestsStorage.model.dto.MortgageRequestDto;
import com.mortgageBank.mortgageRequestsStorage.model.dto.MortgageRequestSearchResult;
import com.mortgageBank.mortgageRequestsStorage.model.dto.MortgageRequestSummary;
import com.mortgageBank.mortgageRequestsStorage.model.enums.MortgageStatus;
import com.mortgageBank.mortgageRequestsStorage.model.enums.QueueType;
import com.mortgageBank.mortgageRequestsStorage.repositories.MortgageRequestsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MortgageRequestsService {

    private final MortgageRequestsRepository mortgageRequestsRepository;
    private final WebClient webClient;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MortgageRequestsService(
            MortgageRequestsRepository mortgageRequestsRepository,
            WebClient webClient,
            MongoTemplate mongoTemplate
    ) {
        this.mortgageRequestsRepository = mortgageRequestsRepository;
        this.webClient = webClient;
        this.mongoTemplate = mongoTemplate;
    }

    public long createMortgageRequest(MortgageRequestDto dto) {
        long id = getNewId();
        log.info("generated id - {}", id);
        MortgageRequest newMortgageRequest = MortgageRequest.of(dto);
        log.info("created new mortgage request from dto");
        newMortgageRequest.setId(id);
        MortgageRequest savedMortgageRequest = mortgageRequestsRepository.save(newMortgageRequest);
        log.info("new mortgage request saved with id {}", savedMortgageRequest.getId());
        return savedMortgageRequest.getId();
    }

    private long getNewId() {
        String path = "/generate-sequence/mortgageRequest";
        log.info("sending http request via webclient");
        Long idResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .build())
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        return idResponse != null ? (long) idResponse : 0;
    }

    public List<MortgageRequestSummary> getRequestsByQueue(QueueType queueType) {
        log.info("fetching all pending requests");
        Criteria criteria = Criteria
                .where("mortgageStatus").is(MortgageStatus.IN_PROGRESS)
                .and("queueType").is(queueType);
        Query query = new Query(criteria)
                .with(Sort.by(Sort.Direction.ASC, "transferTime"));
        return mongoTemplate
                .find(query, MortgageRequest.class)
                .stream()
                .map(MortgageRequestSummary::of)
                .toList();
    }

    public Set<MortgageRequestSearchResult> findRequestsbyIdCard(long identityCardNumber) {
        log.info("fetching requests for user with ID card number {}", identityCardNumber);
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("borrowers.identityCardNumber").is(identityCardNumber),
                Criteria.where("guarantees.identityCardNumber").is(identityCardNumber)
        );
        Query query = new Query(criteria)
                .with(Sort.by(Sort.Direction.ASC, "creationTime"));
        return mongoTemplate
                .find(query, MortgageRequest.class)
                .stream()
                .map(MortgageRequestSearchResult::of)
                .collect(Collectors.toSet());
    }

    public MortgageRequestDto findRequestbyMortgageRequestId(long mortgageRequestId) {
        log.info("fetching request with id {}", mortgageRequestId);
        try {
            return MortgageRequestDto.of(
                    Objects.requireNonNull
                            (mongoTemplate.findById(
                                    mortgageRequestId,
                                    MortgageRequest.class))
            );
        } catch (NullPointerException ex) {
            throw new NullPointerException("Mortgage request with id " + mortgageRequestId + " not found.");
        }
    }

    public void updateMortgageRequestFields(Long id, Map<String, Object> fields) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        fields.forEach(update::set);
        mongoTemplate.updateFirst(query, update, MortgageRequest.class);
    }
}
