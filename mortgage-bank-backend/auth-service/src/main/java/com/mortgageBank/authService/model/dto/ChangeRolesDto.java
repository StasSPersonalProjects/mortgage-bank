package com.mortgageBank.authService.model.dto;

import com.mortgageBank.authService.model.enums.Role;
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
public class ChangeRolesDto {

    @NotNull(message = "Choose at least one role.")
    private Set<Role> roles;
}
