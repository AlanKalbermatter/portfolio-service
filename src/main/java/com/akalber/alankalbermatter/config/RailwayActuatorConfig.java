package com.akalber.alankalbermatter.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("railway")
public class RailwayActuatorConfig {

    @Bean
    public HealthIndicator customHealthIndicator() {
        return () -> Health.up()
                .withDetail("status", "Application is running")
                .withDetail("environment", "Railway")
                .withDetail("timestamp", System.currentTimeMillis())
                .build();
    }
}
