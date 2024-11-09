package com.mortgageBank.authService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static com.mortgageBank.authService.model.enums.Permission.*;
import static com.mortgageBank.authService.model.enums.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final String[] CORS_ALLOWED_SOURCES = {
            "http://localhost:5176",
            "http://localhost:5175"
    };

    private final String[] PERMIT_ALL_LIST = {
            "/auth/employees/authenticate"
    };

    private final String[] ADMIN_ACCESS_LIST = {
            "/auth/employees/roles",
            "/auth/employees/register",
            "/auth/employee-management/**",
            "/auth/employees/active"
    };

    private final String[] ADMIN_CREATE_LIST = {
            "/auth/employees/register"
    };

    private final String[] ADMIN_READ_LIST = {
            "/auth/employee-management/find_user/**",
            "/auth/employees/roles",
            "/auth/employees/active"
    };

    private final String[] ADMIN_PATCH_LIST = {
            "/auth/employee-management/deactivate",
            "/auth/employee-management/restore",
            "/auth/employee-management/change_roles"
    };

    private final String[] CONSULTANT_UNDERWRITER_ACCESS_LIST = {
            "/auth/common/find/all/**"
    };

    private final String[] CONSULTANT_UNDERWRITER_READ_LIST = {
            "/auth/common/find/all/**"
    };

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/auth/employees/change_password")
                            .authenticated();
                    request.requestMatchers(PERMIT_ALL_LIST)
                            .permitAll();
                    request.requestMatchers(ADMIN_ACCESS_LIST)
                            .hasRole(ADMIN.name());
                    request.requestMatchers(POST, ADMIN_CREATE_LIST)
                            .hasAuthority(ADMIN_CREATE.name());
                    request.requestMatchers(GET, ADMIN_READ_LIST)
                            .hasAuthority(ADMIN_READ.name());
                    request.requestMatchers(PATCH, ADMIN_PATCH_LIST)
                            .hasAuthority(ADMIN_UPDATE.name());
                    request.requestMatchers(CONSULTANT_UNDERWRITER_ACCESS_LIST)
                            .hasAnyRole(CONSULTANT.name(), UNDERWRITER.name());
                    request.requestMatchers(GET, CONSULTANT_UNDERWRITER_READ_LIST)
                            .hasAnyAuthority(CONSULTANT_READ.name(), UNDERWRITER_READ.name());
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .securityContext(httpSecuritySecurityContextConfigurer ->
                        httpSecuritySecurityContextConfigurer.requireExplicitSave(false))
                .logout(logout -> logout
                        .logoutUrl("/auth/employees/logout")
                        .permitAll()
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                ((request, response, authentication) ->
                                        SecurityContextHolder.clearContext())));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(CORS_ALLOWED_SOURCES));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
