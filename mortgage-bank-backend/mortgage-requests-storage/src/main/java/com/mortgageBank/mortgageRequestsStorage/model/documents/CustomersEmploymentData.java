package com.mortgageBank.mortgageRequestsStorage.model.documents;

import com.mortgageBank.mortgageRequestsStorage.model.dto.CustomersEmploymentDataDto;
import com.mortgageBank.mortgageRequestsStorage.model.enums.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomersEmploymentData {

    private EmploymentType employmentType;
    private String placeOfEmployment;
    private String position;
    private double durationOfEmploymentInYears;
    private int monthlySalary;
    private int yearlySalary;

    public static CustomersEmploymentData of(CustomersEmploymentDataDto dto) {
        return CustomersEmploymentData
                .builder()
                .employmentType(dto.getEmploymentType())
                .placeOfEmployment(dto.getPlaceOfEmployment())
                .position(dto.getPosition())
                .durationOfEmploymentInYears(dto.getDurationOfEmploymentInYears())
                .monthlySalary(dto.getMonthlySalary())
                .yearlySalary(dto.getYearlySalary())
                .build();
    }
}
