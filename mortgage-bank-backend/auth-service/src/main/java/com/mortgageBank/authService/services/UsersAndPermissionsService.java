package com.mortgageBank.authService.services;

import com.mortgageBank.authService.model.dto.EmployeeDataDto;
import com.mortgageBank.authService.model.entities.User;
import com.mortgageBank.authService.model.enums.Role;
import com.mortgageBank.authService.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;
import java.util.function.Function;

@Service
@Slf4j
public class UsersAndPermissionsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersAndPermissionsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional(readOnly = true)
    public EmployeeDataDto getEmployeeData(String searchBy, String value) {
        Map<String, Function<String, Optional<User>>> searchStrategies = Map.of(
                "username", usersRepository::findByUsername,
                "fullName", usersRepository::findByFullName,
                "idCardNumber", usersRepository::findByIdCardNumber
        );
        Map<String, String> searchByStrings = Map.of(
                "username", "username",
                "fullName", "full name",
                "idCardNumber", "ID card number"
        );
        return searchStrategies.getOrDefault(searchBy, val -> {
                    throw new IllegalArgumentException("Bad search params.");
                }).apply(value)
                .map(EmployeeDataDto::of)
                .orElseThrow(() -> {
                    log.info("User {} not found.", value);
                    return new IllegalArgumentException(
                            "No employee found when searching for " +
                                    value + " by " + searchByStrings.get(searchBy));
                });
    }

    @Transactional
    public void deactivateEmployee(long id, Principal connectedUser) {
        long connectedUserId = usersRepository.findByUsername(connectedUser.getName())
                .map(User::getId)
                .orElseThrow(() -> new IllegalArgumentException("Initiator of the request not found"));
        usersRepository.findById(id).ifPresentOrElse((user) -> {
            if (connectedUserId == user.getId()) {
                log.warn("principal trying to deactivate himself");
                throw new IllegalArgumentException("principal can't deactivate himself");
            }
            if (user.getUsername().equals("admin")) {
                log.warn("cannot deactivate root admin");
                throw new IllegalArgumentException("cannot deactivate root admin");
            }
            usersRepository.deactivateUser(id);
        }, () -> {
            log.info("User with id {} not found.", id);
            throw new IllegalArgumentException("User with id " + id + " not found.");
        });
    }

    @Transactional
    public void restoreEmployee(long id) {
        if (usersRepository.existsById(id)) {
            usersRepository.restoreEmployee(id);
        } else {
            log.info("User with id {} not found.", id);
            throw new IllegalArgumentException("User with id " + id + " not found.");
        }
    }

    @Transactional
    public void changeRoles(long id, Set<Role> roles) {
        if (checkIfAdmin(id)) {
            log.warn("An attempt to change role for root admin");
            throw new IllegalArgumentException("cannot change roles for root admin");
        }
        if (usersRepository.existsById(id) &&
                new HashSet<>(Arrays.asList(Role.values())).containsAll(roles)) {
            String[] roleNames = roles.stream()
                    .map(Role::name)
                    .toArray(String[]::new);
            usersRepository.changeRoles(id, roleNames);
        } else {
            log.info("Invalid roles or user not found");
            throw new IllegalArgumentException("Invalid roles or user not found.");
        }
    }

    private boolean checkIfAdmin(long id) {
        return usersRepository.findByUsername("admin").map(User::getId).orElse(0L) == id;
    }
}
