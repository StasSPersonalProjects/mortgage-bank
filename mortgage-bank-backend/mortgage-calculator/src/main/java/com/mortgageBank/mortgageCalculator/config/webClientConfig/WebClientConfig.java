package com.mortgageBank.mortgageCalculator.config.webClientConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Bean
    public WebClient webClientGetMargins(@Value("${margins.service.url}") String marginsServiceUrl) {
        return WebClient.builder().baseUrl(marginsServiceUrl).build();
    }
}
