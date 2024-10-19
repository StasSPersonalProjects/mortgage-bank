package com.mortgageBank.mortgageRequestsStorage.controller;

import com.mortgageBank.mortgageRequestsStorage.model.dto.MortgageRequestDto;
import com.mortgageBank.mortgageRequestsStorage.model.dto.RequestCreationResponse;
import com.mortgageBank.mortgageRequestsStorage.services.MortgageRequestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/storage/requests")
@Slf4j
public class MortgageRequestsController {

    private final MortgageRequestsService mortgageRequestsService;

    @Autowired
    public MortgageRequestsController(MortgageRequestsService mortgageRequestsService) {
        this.mortgageRequestsService = mortgageRequestsService;
    }

    @PostMapping("/create")
    public ResponseEntity<RequestCreationResponse> createMortgageRequest(
            @RequestBody MortgageRequestDto dto
    ) {
        log.info("received new mortgage request");
        long newRequestID = mortgageRequestsService.createMortgageRequest(dto);
        String result = String.format("Created new request with ID %d", newRequestID);
        RequestCreationResponse response = RequestCreationResponse.builder().message(result).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/request")
    public ResponseEntity<MortgageRequestDto> getRequest(@RequestParam(name = "id") long id) {
        // TODO
        return null;
    }

}
