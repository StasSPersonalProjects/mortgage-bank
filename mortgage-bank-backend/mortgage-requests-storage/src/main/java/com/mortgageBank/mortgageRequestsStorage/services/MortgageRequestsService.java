package com.mortgageBank.mortgageRequestsStorage.services;

import com.mortgageBank.mortgageRequestsStorage.model.documents.MortgageRequest;
import com.mortgageBank.mortgageRequestsStorage.model.dto.MortgageRequestDto;
import com.mortgageBank.mortgageRequestsStorage.repositories.MortgageRequestsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class MortgageRequestsService {

    private final MortgageRequestsRepository mortgageRequestsRepository;
    private final WebClient webClient;

    @Autowired
    public MortgageRequestsService(
            MortgageRequestsRepository mortgageRequestsRepository,
            WebClient webClient
    ) {
        this.mortgageRequestsRepository = mortgageRequestsRepository;
        this.webClient = webClient;
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
}
