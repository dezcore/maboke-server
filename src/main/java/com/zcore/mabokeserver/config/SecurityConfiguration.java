package com.zcore.mabokeserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors-> cors.disable()).csrf().disable()
            .authorizeHttpRequests().anyRequest().permitAll();

       return http.build();
    }
}
