package com.akalber.alankalbermatter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @Autowired(required = false)
    private DataSource dataSource;

    @GetMapping("/actuator/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();

        // Always return UP for basic application health
        response.put("status", "UP");

        Map<String, Object> components = new HashMap<>();

        // Check database if DataSource is available
        if (dataSource != null) {
            try {
                try (Connection connection = dataSource.getConnection()) {
                    if (connection.isValid(3)) {
                        components.put("db", Map.of("status", "UP", "details", Map.of("database", "PostgreSQL", "validConnection", true)));
                    } else {
                        components.put("db", Map.of("status", "DOWN", "details", Map.of("error", "Connection validation failed")));
                    }
                }
            } catch (Exception e) {
                components.put("db", Map.of("status", "DOWN", "details", Map.of("error", e.getMessage())));
            }
        } else {
            components.put("db", Map.of("status", "UNKNOWN", "details", Map.of("error", "DataSource not available")));
        }

        // Always healthy basic components
        components.put("diskSpace", Map.of("status", "UP"));
        components.put("ping", Map.of("status", "UP"));

        response.put("components", components);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> simpleHealth() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));

        if (dataSource != null) {
            try {
                try (Connection connection = dataSource.getConnection()) {
                    if (connection.isValid(3)) {
                        response.put("database", "Connected");
                    } else {
                        response.put("database", "Invalid connection");
                    }
                }
            } catch (Exception e) {
                response.put("database", "Error: " + e.getMessage());
            }
        } else {
            response.put("database", "DataSource not available");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ping")
    public ResponseEntity<Map<String, String>> ping() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok(response);
    }
}
