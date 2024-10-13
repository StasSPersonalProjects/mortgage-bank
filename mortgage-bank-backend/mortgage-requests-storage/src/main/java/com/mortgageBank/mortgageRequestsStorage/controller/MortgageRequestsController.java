package com.mortgageBank.mortgageRequestsStorage.controller;

import com.mortgageBank.mortgageRequestsStorage.model.dto.MortgageRequestDto;
import com.mortgageBank.mortgageRequestsStorage.services.MortgageRequestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/storage/requests")
@Slf4j
public class MortgageRequestsController {

    @Autowired
    private MortgageRequestsService mortgageRequestsService;

    @PostMapping("/create")
    public ResponseEntity<String> createMortgageRequest(@RequestBody MortgageRequestDto mortgageRequestDto) {
        long createdRequestNumber = mortgageRequestsService.createMortgageRequest(mortgageRequestDto);
        String result = String.format(
                "Your request has been successuly created.\nRequest number: %d", createdRequestNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
