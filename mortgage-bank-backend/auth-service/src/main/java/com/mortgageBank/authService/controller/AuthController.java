package com.mortgageBank.authService.controller;

import com.mortgageBank.authService.model.dto.*;
import com.mortgageBank.authService.model.enums.Role;
import com.mortgageBank.authService.services.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth/employees")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerEmployee(
            @RequestBody @Valid RegistrationRequest registrationRequest
    ) {
        log.info("Received request to register new user {} {}",
                registrationRequest.getFirstName(), registrationRequest.getLastName());
        String newEmployee = authService.createUser(registrationRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", newEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<RolesDto>> getAvailableRoles() {
        log.info("Received request to fetch available roles");
        return ResponseEntity.ok()
                .body(Arrays.stream(Role.values())
                        .map(r ->
                                RolesDto.builder()
                                        .id(r.ordinal())
                                        .role(r.name())
                                        .build())
                        .filter(r -> !r.getRole().equals(Role.CUSTOMER.name()))
                        .collect(Collectors.toSet()));
    }

    @GetMapping("/active")
    public ResponseEntity<ActiveEmployeesDto> getActiveEmployees() {
        log.info("Received request to fetch active employees");
        return ResponseEntity.ok().body(authService.getActiveEmployees());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateEmployee(
            @RequestBody @Valid AuthenticationRequest authenticationRequest
    ) {
        log.info("Received request to authenticate a user {}", authenticationRequest.getUsername());
        log.debug("dto {}", authenticationRequest);
        return ResponseEntity.ok().body(authService.authenticateEmployee(authenticationRequest));
    }

    @PatchMapping("/change_password")
    public ResponseEntity<ChangePasswordResult> changePassword(
            @RequestBody @Valid ChangePasswordRequest request,
            Principal connectedUser
    ) {
        log.info("Changing password for {}", connectedUser.getName());
        String attempt = authService.changePassword(request, connectedUser);
        ChangePasswordResult result = ChangePasswordResult.builder().message(attempt).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

}
