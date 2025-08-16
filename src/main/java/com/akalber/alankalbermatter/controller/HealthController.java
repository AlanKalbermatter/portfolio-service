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

    @Autowired
    private DataSource dataSource;

    @GetMapping("/actuator/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();

        try {
            // Test database connection
            try (Connection connection = dataSource.getConnection()) {
                if (connection.isValid(5)) {
                    response.put("status", "UP");
                    response.put("components", Map.of(
                        "db", Map.of("status", "UP", "details", Map.of("database", "PostgreSQL", "validConnection", true)),
                        "diskSpace", Map.of("status", "UP"),
                        "ping", Map.of("status", "UP")
                    ));
                    return ResponseEntity.ok(response);
                }
            }
        } catch (Exception e) {
            response.put("status", "DOWN");
            response.put("components", Map.of(
                "db", Map.of("status", "DOWN", "details", Map.of("error", e.getMessage())),
                "diskSpace", Map.of("status", "UP"),
                "ping", Map.of("status", "UP")
            ));
            return ResponseEntity.status(503).body(response);
        }

        response.put("status", "DOWN");
        response.put("components", Map.of(
            "db", Map.of("status", "DOWN", "details", Map.of("error", "Invalid database connection")),
            "diskSpace", Map.of("status", "UP"),
            "ping", Map.of("status", "UP")
        ));
        return ResponseEntity.status(503).body(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> simpleHealth() {
        Map<String, String> response = new HashMap<>();

        try {
            // Test database connection
            try (Connection connection = dataSource.getConnection()) {
                if (connection.isValid(5)) {
                    response.put("status", "UP");
                    response.put("database", "Connected");
                    return ResponseEntity.ok(response);
                }
            }
        } catch (Exception e) {
            response.put("status", "DOWN");
            response.put("error", e.getMessage());
            return ResponseEntity.status(503).body(response);
        }

        response.put("status", "DOWN");
        response.put("error", "Database connection invalid");
        return ResponseEntity.status(503).body(response);
    }
}
