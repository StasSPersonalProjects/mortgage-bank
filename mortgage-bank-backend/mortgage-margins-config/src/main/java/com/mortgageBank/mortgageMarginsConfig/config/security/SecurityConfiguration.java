package com.mortgageBank.mortgageMarginsConfig.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static com.mortgageBank.mortgageMarginsConfig.model.enums.Permission.*;
import static com.mortgageBank.mortgageMarginsConfig.model.enums.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final String[] ADMIN_ACCESS_LIST = {
            "/margins/config/create",
            "/margins/config/update/zero-margin-rates",
            "/margins/config/delete/**",
            "/margins/config/types",
            "/basic_rates/config/create",
            "/basic_rates/config/set",
            "/basic_rates/config/delete/**"
    };

    private final String[] ADMIN_CREATE_LIST = {
            "/margins/config/create",
            "/basic_rates/config/create"
    };

    private final String[] ADMIN_READ_LIST = {
            "/margins/config/types"
    };

    private final String[] ADMIN_UPDATE_LIST = {
            "/margins/config/update/zero-margin-rates",
            "/basic_rates/config/set"
    };

    private final String[] ADMIN_DELETE_LIST = {
            "/margins/config/delete/**",
            "/basic_rates/config/delete/**"
    };

    private final String[] UNDERWRITER_ACCESS_LIST = {
            "/margins/config/get-margin/**",
            "/basic_rates/config/get_basics/**"
    };

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(ADMIN_ACCESS_LIST)
                            .hasRole(ADMIN.name());
                    request.requestMatchers(GET, ADMIN_READ_LIST)
                            .hasAnyAuthority(ADMIN_READ.name());
                    request.requestMatchers(POST, ADMIN_CREATE_LIST)
                            .hasAuthority(ADMIN_CREATE.name());
                    request.requestMatchers(PUT, ADMIN_UPDATE_LIST)
                            .hasAuthority(ADMIN_UPDATE.name());
                    request.requestMatchers(DELETE, ADMIN_DELETE_LIST)
                            .hasAuthority(ADMIN_DELETE.name());
                    request.requestMatchers(UNDERWRITER_ACCESS_LIST)
                            .hasAnyRole(UNDERWRITER.name(), ADMIN.name());
                    request.requestMatchers(GET, UNDERWRITER_ACCESS_LIST)
                            .hasAnyAuthority(UNDERWRITER_READ.name(), ADMIN_READ.name());
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5176", "http://localhost:5175"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
