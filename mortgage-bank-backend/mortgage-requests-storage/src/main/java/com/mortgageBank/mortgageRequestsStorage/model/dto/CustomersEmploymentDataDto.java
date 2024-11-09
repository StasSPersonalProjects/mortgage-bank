package com.mortgageBank.mortgageRequestsStorage.model.dto;

import com.mortgageBank.mortgageRequestsStorage.model.documents.CustomersEmploymentData;
import com.mortgageBank.mortgageRequestsStorage.model.enums.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomersEmploymentDataDto {

    private EmploymentType employmentType;
    private String placeOfEmployment;
    private String position;
    private double durationOfEmploymentInYears;
    private int monthlySalary;
    private int yearlySalary;

    public static CustomersEmploymentDataDto of(CustomersEmploymentData employmentData) {
        return CustomersEmploymentDataDto
                .builder()
                .employmentType(employmentData.getEmploymentType())
                .placeOfEmployment(employmentData.getPlaceOfEmployment())
                .position(employmentData.getPosition())
                .durationOfEmploymentInYears(employmentData.getDurationOfEmploymentInYears())
                .monthlySalary(employmentData.getMonthlySalary())
                .yearlySalary(employmentData.getYearlySalary())
                .build();
    }
}
