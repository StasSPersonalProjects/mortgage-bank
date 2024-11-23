package com.mortgageBank.mortgageRequestsStorage.controller;

import com.mortgageBank.mortgageRequestsStorage.model.dto.MortgageRequestDto;
import com.mortgageBank.mortgageRequestsStorage.model.dto.MortgageRequestSummary;
import com.mortgageBank.mortgageRequestsStorage.model.dto.RequestCreationResponse;
import com.mortgageBank.mortgageRequestsStorage.model.dto.UpdateRequestDto;
import com.mortgageBank.mortgageRequestsStorage.model.enums.QueueType;
import com.mortgageBank.mortgageRequestsStorage.services.MortgageRequestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/add_field")
    public ResponseEntity<String> addFieldToExistingRequest(
            @RequestParam("request_id") long requestId,
            @RequestParam("field") String field,
            @RequestBody Object element
    ) {
        log.info("received request to add new element to existing field {} of mortgage request #{}",
                field, requestId);
        mortgageRequestsService.addFieldToExistingRequest(requestId, field, element);
        return ResponseEntity.ok("Modified successfully");
    }

    @GetMapping("/pending")
    public ResponseEntity<List<MortgageRequestSummary>> getRequestsByQueue(
            @RequestParam("queue_type")QueueType queueType
    ) {
        log.info("received request to fetch all pending requests");
        return ResponseEntity.ok(mortgageRequestsService.getRequestsByQueue(queueType));
    }

    @GetMapping("/request/by")
    public ResponseEntity<?> searchRequestsBy(
            @RequestParam(name = "search_param") String searchParam,
            @RequestParam(name = "search_value") long searchValue
    ) {
        log.info("received http request to search requests by {} for {}", searchParam, searchValue);
        return searchParam.equals("idCardNumber") ?
                ResponseEntity.ok(mortgageRequestsService.findRequestsbyIdCard(String.valueOf(searchValue))) :
                ResponseEntity.ok(mortgageRequestsService.findRequestbyMortgageRequestId(searchValue));
    }

    @PutMapping("/update_request")
    public ResponseEntity<?> updateMortgageRequestFields(
            @RequestBody UpdateRequestDto updateRequest
    ) {
        log.info("received request to update mortgage request #{}", updateRequest.getId());
        mortgageRequestsService
                .updateMortgageRequestFields(
                        updateRequest.getId(),
                        updateRequest.getFields()
                );
        return ResponseEntity.ok("Saved successfully!");
    }

}
