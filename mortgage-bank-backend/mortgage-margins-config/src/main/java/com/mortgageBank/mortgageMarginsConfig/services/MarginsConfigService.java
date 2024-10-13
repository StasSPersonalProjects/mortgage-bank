package com.mortgageBank.mortgageMarginsConfig.services;

import com.mortgageBank.mortgageMarginsConfig.model.dto.LoanRateTypeDto;
import com.mortgageBank.mortgageMarginsConfig.model.entities.LoanRateType;
import com.mortgageBank.mortgageMarginsConfig.model.enums.LoanType;
import com.mortgageBank.mortgageMarginsConfig.repositories.MarginsConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Map;

@Service
@Slf4j
public class MarginsConfigService {

    private final MarginsConfigRepository marginsConfigRepository;

    @Autowired
    public MarginsConfigService(MarginsConfigRepository marginsConfigRepository) {
        this.marginsConfigRepository = marginsConfigRepository;
    }

    @Transactional
    @CacheEvict(value = "loanMargins", allEntries = true)
    public LoanRateType createLoanRateType(LoanRateTypeDto dto) {
        LoanRateType newRateType = LoanRateType.of(dto);
        return marginsConfigRepository.save(newRateType);
    }

    @Cacheable(value = "loanMargins", key = "#loanRateType + '-' + #months + '-' + #interestRate")
    public Double getLoanRateMarginsByDuration(String loanRateType, int months, double interestRate) {
        String range = determineRange(months);
        log.debug("range determined - {}", range);
        DecimalFormat df = new DecimalFormat("##.##");
        return marginsConfigRepository.findByLoanRateType(loanRateType)
                .map(existingLoanRateType -> {
                    double requestedMarginRate = existingLoanRateType.getZeroMarginRates().get(range);
                    return Double.valueOf(df.format(interestRate - requestedMarginRate));
                })
                .orElseThrow(() -> new IllegalArgumentException(
                        "Margin not found for the given range and loan type")
                );
    }

    private String determineRange(int months) {
        int level = months / 60;
        return switch (level) {
            case 0 -> "0-59";
            case 1 -> "60-119";
            case 2 -> "120-179";
            case 3 -> "180-239";
            case 4 -> "240-299";
            case 5 -> "300-359";
            case 6 -> "360";
            default -> "months out of range";
        };
    }

    @Transactional
    @CacheEvict(value = "loanMargins", allEntries = true)
    public void updateZeroMarginRates(String loanType, Map<String, Double> newZeroMarginRates) {
        marginsConfigRepository
                .findByLoanRateType(loanType)
                .ifPresentOrElse(existingLoanRateType -> {
                    Map<String, Double> zeroMarginRatess = existingLoanRateType.getZeroMarginRates();
                    zeroMarginRatess.clear();
                    zeroMarginRatess.putAll(newZeroMarginRates);
                    existingLoanRateType.setZeroMarginRates(zeroMarginRatess);
                    marginsConfigRepository.save(existingLoanRateType);
                }, () -> {
                    throw new IllegalArgumentException(
                            "Loan rate type not found");
                });
    }

    @Transactional
    @CacheEvict(value = "loanMargins", key = "#loanType.name() + '-' + '*'")
    public void deleteLoanRateType(LoanType loanType) {
        marginsConfigRepository.deleteByName(loanType.name());
    }
}
