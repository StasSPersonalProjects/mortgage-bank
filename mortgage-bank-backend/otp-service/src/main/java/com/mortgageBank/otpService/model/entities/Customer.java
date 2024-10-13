package com.mortgageBank.otpService.model.entities;

import com.mortgageBank.otpService.model.dto.CustomerDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public static Customer of(CustomerDto dto) {
        return Customer.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }

}
