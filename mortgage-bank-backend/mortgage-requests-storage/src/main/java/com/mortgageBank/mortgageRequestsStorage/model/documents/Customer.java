package com.mortgageBank.mortgageRequestsStorage.model.documents;

import com.mortgageBank.mortgageRequestsStorage.model.dto.CustomerDto;
import com.mortgageBank.mortgageRequestsStorage.model.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    private String identityCardNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private MaritalStatus maritalStatus;
    private int childrenUnderAge21;
    private Set<CustomersEmploymentData> employmentData;
    private Set<Spending> spendings;
    private Set<ExtraIncome> extraIncomes;
    private String extraInfo;

    private Set<Long> mortgageRequestsAsBorrowerIds;
    private Set<Long> mortgageRequestsAsGuaranteeIds;

    public static Customer of(CustomerDto dto) {
        return Customer.builder()
                .identityCardNumber(dto.getIdentityCardNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .maritalStatus(dto.getMaritalStatus())
                .childrenUnderAge21(dto.getChildrenUnderAge21())
                .employmentData(dto.getEmploymentData()
                        .stream()
                        .map(CustomersEmploymentData::of)
                        .collect(Collectors.toSet()))
                .spendings(dto.getSpendings()
                        .stream()
                        .map(Spending::of)
                        .collect(Collectors.toSet()))
                .extraIncomes(dto.getExtraIncomes()
                        .stream()
                        .map(ExtraIncome::of)
                        .collect(Collectors.toSet()))
                .extraInfo(dto.getExtraInfo())
                .build();
    }
}
