package com.mortgageBank.authService.services;

import com.mortgageBank.authService.model.dto.*;
import com.mortgageBank.authService.model.entities.Token;
import com.mortgageBank.authService.model.entities.User;
import com.mortgageBank.authService.model.enums.Role;
import com.mortgageBank.authService.model.enums.TokenType;
import com.mortgageBank.authService.repositories.TokensRepository;
import com.mortgageBank.authService.repositories.UsersRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {

    private final UsersRepository usersRepository;
    private final TokensRepository tokensRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final String initialAdminPassword;
    private final String initialUnderwriterPassword;

    private static final Map<String, Integer> ROLES_CODES_MAP = Map.of(
            "ADMIN", 6875,
            "CUSTOMER", 7485,
            "UNDERWRITER", 1349
    );

    @Autowired
    public AuthService(UsersRepository usersRepository,
                       TokensRepository tokensRepository,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder,
                       @Value("${initial.admin.password}") String initialAdminPassword,
                       @Value("${initial.underwriter.password}") String initialUnderwriterPassword) {
        this.usersRepository = usersRepository;
        this.tokensRepository = tokensRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.initialAdminPassword = initialAdminPassword;
        this.initialUnderwriterPassword = initialUnderwriterPassword;
    }

    @Transactional
    public String createUser(RegistrationRequest registrationRequest) {
        if (usersRepository.findByIdCardNumber(registrationRequest.getIdCardNumber()).isEmpty()) {
            User newUser = User.of(registrationRequest);
            newUser.setRoles(registrationRequest.getRoles());
            if (registrationRequest.getRoles().contains(Role.UNDERWRITER)) {
                newUser.setPassword(passwordEncoder.encode(initialUnderwriterPassword));
            } else if (registrationRequest.getRoles().contains(Role.ADMIN)) {
                newUser.setPassword(passwordEncoder.encode(initialAdminPassword));
            }
            User createdEmployee = usersRepository.save(newUser);
            String newEmployeeUsername = String.format("u%s%s%d",
                    registrationRequest.getFirstName().toLowerCase().substring(0, 2),
                    registrationRequest.getLastName().toLowerCase().substring(0, 2),
                    createdEmployee.getId());
            createdEmployee.setUsername(newEmployeeUsername);
            User newEmployee = usersRepository.save(createdEmployee);
            return String.format("Created new employee %s with username %s",
                    newEmployee.getFullName(), newEmployee.getUsername());
        } else {
            log.info("Employee already exists");
            throw new IllegalArgumentException("Employee already exists");
        }
    }

    @Transactional
    public AuthenticationResponse authenticateEmployee(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        User user = usersRepository.findByEmailAndUserIsActive(authenticationRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        Set<Integer> userRoles = new HashSet<>();
        ROLES_CODES_MAP.forEach((key, value) -> {
            if (user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()).contains(key)) {
                userRoles.add(value);
            }
        });
        String newToken = jwtService.generateToken(user);
        LocalDateTime newLastLogin = LocalDateTime.now();
        usersRepository.updateLastLoginDateTime(newLastLogin, user.getId());
        revokeAllUsersTokens(user);
        saveUserToken(user, newToken);
        return AuthenticationResponse
                .builder()
                .accessToken(newToken)
                .fullName(user.getFullName())
                .roles(userRoles)
                .build();
    }

    @Transactional(readOnly = true)
    public ActiveEmployeesDto getActiveEmployees() {
        int activeEmployees = usersRepository.countActiveUsers();
        return ActiveEmployeesDto.builder().activeEmployees(activeEmployees).build();
    }

    @Transactional
    public String changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = ((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            log.info("Wrong password at password change attempt");
            throw new IllegalArgumentException("Wrong password");
        }
        if (!comparePasswords(request.getNewPassword(), request.getConfirmationPassword())) {
            log.info("Passwords are not the same at password change attempt");
            throw new IllegalArgumentException("Passwords are not the same.");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        usersRepository.save(user);
        return String.format("Password for user %s changed successfully!", connectedUser.getName());
    }

    private boolean comparePasswords(String password, String confirmedPassword) {
        return password.equals(confirmedPassword);
    }

    @Transactional
    private void revokeAllUsersTokens(User user) {
        log.debug("Revoking all tokens for user - {}", user.getUsername());
        List<Token> tokens = tokensRepository
                .findAllValidTokensByUser(user.getId())
                .orElse(new ArrayList<>());
        if (tokens.isEmpty()) {
            return;
        }
        tokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokensRepository.saveAll(tokens);
    }

    @Transactional
    private void saveUserToken(User user, String accessToken) {
        log.debug("Saving token for user {}", user.getUsername());
        Token token = Token.builder()
                .token(accessToken)
                .user(user)
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokensRepository.save(token);
    }

    @PostConstruct
    @Transactional
    public void createInitialAdmin() {
        if (usersRepository.findByUsername("admin").isEmpty()) {
            RegistrationRequest registrationRequest = RegistrationRequest.builder()
                    .idCardNumber("999999999")
                    .firstName("root")
                    .lastName("admin")
                    .roles(Collections.singleton(Role.ADMIN))
                    .build();
            User initialAdmin = User.of(registrationRequest);
            initialAdmin.setUsername("admin");
            initialAdmin.setPassword(passwordEncoder.encode(initialAdminPassword));
            usersRepository.save(initialAdmin);
        }
    }
}