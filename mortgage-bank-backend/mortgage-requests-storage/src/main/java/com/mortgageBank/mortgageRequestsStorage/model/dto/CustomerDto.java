package com.mortgageBank.mortgageRequestsStorage.model.dto;

import com.mortgageBank.mortgageRequestsStorage.model.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    private long identityCardNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private MaritalStatus maritalStatus;
    private int childrenUnderAge21;
    private List<CustomersEmploymentDataDto> employmentData;
    private List<SpendingDto> spendings;
    private List<ExtraIncomeDto> extraIncomes;
    private String extraInfo;
}
