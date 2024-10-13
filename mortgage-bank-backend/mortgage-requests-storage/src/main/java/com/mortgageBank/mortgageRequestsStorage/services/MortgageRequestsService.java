package com.mortgageBank.mortgageRequestsStorage.services;

import com.mortgageBank.mortgageRequestsStorage.model.dto.MortgageRequestDto;
import com.mortgageBank.mortgageRequestsStorage.model.entities.MortgageRequest;
import com.mortgageBank.mortgageRequestsStorage.model.enums.MortgageStatus;
import com.mortgageBank.mortgageRequestsStorage.repositories.CustomersEmploymentDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class MortgageRequestsService {

    @Autowired
    private CustomersEmploymentDataRepository customersEmploymentDataRepository;

    public long createMortgageRequest(MortgageRequestDto mortgageRequestDto) {
        MortgageRequest newMortgageRequest = new MortgageRequest();
        LocalDate requestCreationDate = LocalDate.now();
        newMortgageRequest.setCreationDate(requestCreationDate);
        newMortgageRequest.setMortgageStatus(MortgageStatus.IN_PROGRESS);


        return 0;
    }
}
