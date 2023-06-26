package com.zcore.mabokeserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {
        
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http.httpBasic().disable().csrf().disable()
        .authorizeExchange(exchanges -> 
        exchanges.pathMatchers("/view/**").permitAll()
        .pathMatchers("/gtoken/**").permitAll()
        .pathMatchers("/gfiles/**").permitAll()
        .pathMatchers("/gfile/**").permitAll()
         .pathMatchers("/gpermission/**").permitAll()
        .pathMatchers("/conflict/**").permitAll()
        .pathMatchers("/category/**").permitAll()  
        .pathMatchers("/serie/**").permitAll()
        .anyExchange().permitAll()
        )
        .build();
    }
}
