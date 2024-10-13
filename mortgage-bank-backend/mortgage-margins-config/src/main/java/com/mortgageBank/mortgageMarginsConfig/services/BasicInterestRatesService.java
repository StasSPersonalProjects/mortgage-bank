package com.mortgageBank.mortgageMarginsConfig.services;

import com.mortgageBank.mortgageMarginsConfig.model.dto.AdjustableRateDto;
import com.mortgageBank.mortgageMarginsConfig.model.entities.BasicAdjustableInterestRate;
import com.mortgageBank.mortgageMarginsConfig.repositories.BasicAdjustableInterestRatesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BasicInterestRatesService {

    @Autowired
    private BasicAdjustableInterestRatesRepository basicAdjustableInterestRatesRepository;

    public void createBasicAdjustableInterestRates(List<AdjustableRateDto> adjustableRateDtoList) {
        adjustableRateDtoList.stream()
                .map(BasicAdjustableInterestRate::of)
                .forEach(basicAdjustableInterestRatesRepository::save);
    }

    public Double getBasicAdjustableInterestRate(String loanType) {
        return basicAdjustableInterestRatesRepository
                .findBasicInterestRateByLoanType(loanType)
                .orElseThrow(() -> new IllegalArgumentException("Illegal loan type"));
    }

    public void setBasicAdjustableInterestRates(List<AdjustableRateDto> adjustableRateDtoList) {
        adjustableRateDtoList.stream()
                .map(BasicAdjustableInterestRate::of)
                .forEach(r -> basicAdjustableInterestRatesRepository
                        .findByLoanType(r.getLoanType())
                        .ifPresentOrElse(
                                e -> {
                                    e.setBasicInterestRate(r.getBasicInterestRate());
                                    log.debug("set basic interest rate for {} to {}",
                                            e.getLoanType(), r.getBasicInterestRate());
                                    basicAdjustableInterestRatesRepository.save(e);
                                },
                                () -> {throw new IllegalArgumentException("Illegal loan type");}
                        )
                );
    }

    public void deleteBasicInterestRate(String loanType) {
        basicAdjustableInterestRatesRepository
                .findByLoanType(loanType)
                .ifPresentOrElse(
                        e -> {
                            int id = e.getId();
                            log.debug("Found {} with id {}", loanType, id);
                            basicAdjustableInterestRatesRepository.deleteById(id);
                        },
                        () -> {throw new IllegalArgumentException("Illegal loan type");}
                );
    }
}
