package com.mortgageBank.authService.controller;

import com.mortgageBank.authService.model.dto.ChangeRolesDto;
import com.mortgageBank.authService.model.dto.EmployeeDataDto;
import com.mortgageBank.authService.services.UsersAndPermissionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth/employee-management")
@Slf4j
public class UsersAndPermissionsController {

    private final UsersAndPermissionsService usersAndPermissionsService;

    @Autowired
    public UsersAndPermissionsController(UsersAndPermissionsService usersAndPermissionsService) {
        this.usersAndPermissionsService = usersAndPermissionsService;
    }

    @GetMapping("/find_user")
    public ResponseEntity<EmployeeDataDto> getEmployeeData(
            @RequestParam(name = "search_by") String searchBy,
            @RequestParam(name = "search_param") String value
    ) {
        log.info("Received request for fetching data for employee {} using {} search", value, searchBy);
        return ResponseEntity.ok().body(usersAndPermissionsService.getEmployeeData(searchBy, value));
    }

    @PatchMapping("/deactivate")
    public ResponseEntity<String> deactivateEmployee(
            @RequestParam(name = "id") long id,
            Principal connectedUser
    ) {
        log.info("Received request to deactivate employee with id {}", id);
        usersAndPermissionsService.deactivateEmployee(id, connectedUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @PatchMapping("/restore")
    public ResponseEntity<Void> restoreEmployee(
            @RequestParam(name = "id") long id
    ) {
        log.info("Received request to restore employee with id {}", id);
        usersAndPermissionsService.restoreEmployee(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @PatchMapping("/change_roles")
    public ResponseEntity<Void> changeRoles(
            @RequestParam(name = "id") long id,
            @RequestBody ChangeRolesDto roles
            ) {
        log.info("Received request to change roles for employee with id {} to {}",
                id, roles.getRoles());
        usersAndPermissionsService.changeRoles(id, roles.getRoles());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }
}
