package com.mortgageBank.mortgageCalculator.controller;

import com.mortgageBank.mortgageCalculator.model.LoanRequestDto;
import com.mortgageBank.mortgageCalculator.model.PaymentAndMargin;
import com.mortgageBank.mortgageCalculator.services.MortgageCalculatorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculator")
@Slf4j
public class MortgageCalculatorController {

    private final MortgageCalculatorService mortgageCalculatorService;

    @Autowired
    public MortgageCalculatorController (MortgageCalculatorService mortgageCalculatorService) {
        this.mortgageCalculatorService = mortgageCalculatorService;
    }

    @PostMapping("/calculate/payment_margin")
    public ResponseEntity<PaymentAndMargin> calculateMonthlyPaymentGetMargin(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid LoanRequestDto loanRequestDto
    ) {
        log.info(
                "Received request for calculating payment and margin for loan of type {} " +
                        "of amount {} with interest rate {} and duration {} months",
                loanRequestDto.getLoanType().name(),
                loanRequestDto.getLoanAmount(),
                loanRequestDto.getAnnualInterestRate(),
                loanRequestDto.getLoanTermInMonths()
        );
        return ResponseEntity.ok()
                .body(mortgageCalculatorService.calculateMonthlyPaymentGetMargin(loanRequestDto, token));
    }
}
