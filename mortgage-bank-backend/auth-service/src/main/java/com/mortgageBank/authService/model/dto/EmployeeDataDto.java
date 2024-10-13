package com.mortgageBank.authService.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mortgageBank.authService.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDataDto {

    private long id;
    private String fullName;
    private String username;
    private Set<RolesDto> roles;
    @JsonProperty("isActive")
    private boolean isActive;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastLogin;

    public static EmployeeDataDto of(User user) {
        boolean userActiveStatus = user.isEnabled() && user.isAccountNonLocked();
        return EmployeeDataDto
                .builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .roles(user.getRoles()
                        .stream()
                        .map(r ->
                                RolesDto.builder()
                                        .id(r.ordinal())
                                        .role(r.name())
                                        .build())
                        .collect(Collectors.toSet()))
                .isActive(userActiveStatus)
                .lastLogin(user.getLastLogin())
                .build();
    }

}
