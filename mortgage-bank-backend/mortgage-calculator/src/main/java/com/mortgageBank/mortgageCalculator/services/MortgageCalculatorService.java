package com.mortgageBank.mortgageCalculator.services;

import com.mortgageBank.mortgageCalculator.model.LoanRequestDto;
import com.mortgageBank.mortgageCalculator.model.PaymentAndMargin;
import com.mortgageBank.mortgageCalculator.utils.calculator.MonthlyPaymentCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;

@Service
@Slf4j
public class MortgageCalculatorService {

    @Value("${margins.service.get-margins.endpoint}")
    private String getMarginsEndpoint;

    @Autowired
    private WebClient webClientGetMargins;

    @Autowired
    private MonthlyPaymentCalculator calculator;

    public PaymentAndMargin calculateMonthlyPaymentGetMargin(
            LoanRequestDto loanRequest,
            String token
    ) {
        DecimalFormat df = new DecimalFormat("#####.##");
        double payment = Double.parseDouble(df.format(calculator.calculateMonthlyPayment(
                loanRequest.getLoanAmount(),
                loanRequest.getAnnualInterestRate(),
                loanRequest.getLoanTermInMonths()
        )));
        log.debug("payment calculated - {}", payment);
        Double marginResponse = webClientGetMargins.get()
                .uri(uriBuilder -> uriBuilder
                        .path(getMarginsEndpoint)
                        .queryParam("loan_type", loanRequest.getLoanType().name())
                        .queryParam("duration", loanRequest.getLoanTermInMonths())
                        .queryParam("interest_rate", loanRequest.getAnnualInterestRate())
                        .build())
//                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Double.class)
                .switchIfEmpty(Mono.error(new RuntimeException("No margin returned from the service")))
                .block();
        double marginInPercent = (marginResponse != null) ? marginResponse : 0.0;
        log.debug("margin received from margins microservice - {}", marginInPercent);
        double marginInCurrency = Double.parseDouble(
                df.format(loanRequest.getLoanAmount() * (marginInPercent / 100)));
        return PaymentAndMargin.builder()
                .payment(payment)
                .marginInPercent(marginInPercent)
                .marginInCurrency(marginInCurrency)
                .build();
    }
}
