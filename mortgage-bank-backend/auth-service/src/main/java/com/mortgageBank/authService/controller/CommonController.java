package com.mortgageBank.authService.controller;

import com.mortgageBank.authService.model.dto.AvailableUserDto;
import com.mortgageBank.authService.services.UsersAndPermissionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/auth/common")
@Slf4j
public class CommonController {

    private final UsersAndPermissionsService usersAndPermissionsService;

    @Autowired
    public CommonController(UsersAndPermissionsService usersAndPermissionsService) {
        this.usersAndPermissionsService = usersAndPermissionsService;
    }

    @GetMapping("/find/all")
    public ResponseEntity<Set<AvailableUserDto>> getAvailableUsersByRole(
            @RequestParam(name = "role") String role
    ) {
        log.info("Received request to fetch all available users with role {}", role);
        return ResponseEntity.ok(usersAndPermissionsService.getUsersByRole(role));
    }
}
