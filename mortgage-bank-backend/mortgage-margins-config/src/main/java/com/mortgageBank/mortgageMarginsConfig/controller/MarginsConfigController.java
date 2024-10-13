package com.mortgageBank.mortgageMarginsConfig.controller;

import com.mortgageBank.mortgageMarginsConfig.model.dto.AvailableLoanTypesDto;
import com.mortgageBank.mortgageMarginsConfig.model.dto.LoanRateTypeDto;
import com.mortgageBank.mortgageMarginsConfig.model.entities.LoanRateType;
import com.mortgageBank.mortgageMarginsConfig.model.enums.LoanType;
import com.mortgageBank.mortgageMarginsConfig.services.MarginsConfigService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mortgageBank.mortgageMarginsConfig.model.enums.LoanType.*;

@RestController
@RequestMapping("/margins/config")
@Slf4j
public class MarginsConfigController {

    private final MarginsConfigService marginsConfigService;

    private static final Map<LoanType, String> mappedLoanTypes = Map.of(
            FIXED_RATE_NON_INDEXED, "Fixed interest rate",
            FIXED_RATE_INDEX_LINKED, "Fixed interest rate index linked",
            PRIME_RATE, "Prime interest rate",
            ADJUSTABLE_RATE_X2_NON_INDEXED, "Adjustable interest rate 2+2",
            ADJUSTABLE_RATE_X2_INDEX_LINKED, "Adjustable interest rate 2+2 index linked",
            ADJUSTABLE_RATE_X5_NON_INDEXED, "Adjustable interest rate 5+5",
            ADJUSTABLE_RATE_X5_INDEX_LINKED, "Adjustable interest rate 5+5 index linked"
    );

    @Autowired
    public MarginsConfigController(MarginsConfigService marginsConfigService) {
        this.marginsConfigService = marginsConfigService;
    }

    @GetMapping("/types")
    public ResponseEntity<Set<AvailableLoanTypesDto>> getAvailableLoanTypes() {
        log.info("Received request to fetch all available loan types");
        return ResponseEntity.ok().body(Arrays.stream(LoanType.values())
                .map(l ->
                        AvailableLoanTypesDto
                                .builder()
                                .id(l.ordinal())
                                .loanType(mappedLoanTypes.get(l))
                                .build())
                .collect(Collectors.toSet()));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createLoanRateType(@RequestBody @Valid LoanRateTypeDto dto) {
        log.info("Received request for creating new loan rate type {}", dto.getLoanType().name());
        LoanRateType newLoanRateType = marginsConfigService.createLoanRateType(dto);
        String result = String.format("Created new loan rate type %s with id %d",
                newLoanRateType.getLoanType(), newLoanRateType.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/get-margin")
    public ResponseEntity<?> getLoanRateMargins(
            @RequestParam("loan_type") String loanType,
            @RequestParam("duration") int months,
            @RequestParam("interest_rate") double interestRate
    ) {
        log.debug("Received request for fetching margin for {} for {} months at {} interest rate",
                loanType, months, interestRate);
        if (!isDataValid(loanType, months, interestRate)) {
            return ResponseEntity.badRequest().body("Bad data provided");
        }
        return ResponseEntity.ok().body(marginsConfigService
                .getLoanRateMarginsByDuration(loanType, months, interestRate));
    }

    @PutMapping("/update/zero-margin-rates")
    public ResponseEntity<Void> updateZeroMarginRates(@RequestBody @Valid LoanRateTypeDto dto) {
        log.debug("Received request for updating zero margins rate for {}", dto.getLoanType().name());
        marginsConfigService.updateZeroMarginRates(dto.getLoanType().name(), dto.getZeroMarginRates());
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteLoanRateType(@RequestParam("loan_type") LoanType loanType) {
        log.debug("Received request for deleting loan type {}", loanType.name());
        marginsConfigService.deleteLoanRateType(loanType);
        return ResponseEntity.ok().body(null);
    }

    private boolean isDataValid(String loanType, int months, double interestRate) {
        boolean isLoanTypeValid = Arrays
                .stream(LoanType.values())
                .anyMatch(existingLoanType ->
                        existingLoanType.name().equals(loanType)
                );
        boolean isMonthsValid = months > 0 && months <= 360;
        boolean isInterestRateValid = interestRate > 0;
        return isLoanTypeValid && isMonthsValid && isInterestRateValid;
    }
}