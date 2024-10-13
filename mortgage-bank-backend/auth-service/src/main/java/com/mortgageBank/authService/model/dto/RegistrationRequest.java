package com.mortgageBank.authService.model.dto;

import com.mortgageBank.authService.model.enums.Role;
import com.mortgageBank.authService.model.validation.ValidIsraeliId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {

    @ValidIsraeliId
    @NotNull
    private String idCardNumber;
    @NotBlank(message = "Please enter first name.")
    private String firstName;
    @NotBlank(message = "Please enter last name.")
    private String lastName;
    private Set<Role> roles;
}
