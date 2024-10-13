package com.mortgageBank.mortgageMarginsConfig.controller;

import com.mortgageBank.mortgageMarginsConfig.model.dto.AdjustableRateDto;
import com.mortgageBank.mortgageMarginsConfig.model.enums.LoanType;
import com.mortgageBank.mortgageMarginsConfig.services.BasicInterestRatesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basic_rates/config")
@Slf4j
public class BasicInterestRatesController {

    @Autowired
    private BasicInterestRatesService basicInterestRatesService;

    @PostMapping("/create")
    public ResponseEntity<Void> createBasicInterestRate(
            @RequestBody List<AdjustableRateDto> adjustableRateDtoList) {
        log.debug("Received request for creating new adjustable basic interest rates: {}",
                adjustableRateDtoList.toString());
        basicInterestRatesService.createBasicAdjustableInterestRates(adjustableRateDtoList);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/get_basics")
    public ResponseEntity<Double> getBasicAdjustableInterestRate(
            @RequestParam(name = "type") LoanType loanType) {
        log.debug("Received request for fetching basic interest rate for {}", loanType.name());
        return ResponseEntity.ok().body(basicInterestRatesService
                .getBasicAdjustableInterestRate(loanType.name()));
    }

    @PatchMapping("/set")
    public ResponseEntity<Void> setBasicInterestRate(
            @RequestBody List<AdjustableRateDto> adjustableRateDtoList) {
        log.debug("Received request for updating adjustable basic interest rates: {}",
                adjustableRateDtoList.toString());
        basicInterestRatesService.setBasicAdjustableInterestRates(adjustableRateDtoList);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteBasicInterestRate(
            @RequestParam(name = "type") LoanType loanType) {
        log.debug("Received request for deleting basic interest rate for {}", loanType.name());
        basicInterestRatesService.deleteBasicInterestRate(loanType.name());
        return ResponseEntity.ok().body(null);
    }
}
