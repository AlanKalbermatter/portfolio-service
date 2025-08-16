package com.akalber.alankalbermatter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
@Profile("railway")
public class RailwayDataSourceConfig {

    @Value("${DATABASE_URL}")
    private String databaseUrl;

    @Value("${PGUSER:#{null}}")
    private String pgUser;

    @Value("${PGPASSWORD:#{null}}")
    private String pgPassword;

    @Value("${PGHOST:#{null}}")
    private String pgHost;

    @Value("${PGPORT:5432}")
    private String pgPort;

    @Value("${PGDATABASE:#{null}}")
    private String pgDatabase;

    @Bean
    public DataSource dataSource() {
        try {
            String jdbcUrl;
            String username;
            String password;

            // First try to use Railway's individual environment variables if available
            if (pgHost != null && pgUser != null && pgPassword != null && pgDatabase != null) {
                jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s", pgHost, pgPort, pgDatabase);
                username = pgUser;
                password = pgPassword;
            } else {
                // Fallback to parsing DATABASE_URL
                URI dbUri = new URI(databaseUrl);

                String host = dbUri.getHost();
                int port = dbUri.getPort() != -1 ? dbUri.getPort() : 5432;
                String database = dbUri.getPath().substring(1); // Remove leading '/'

                jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);

                // Check if credentials are in the URL
                if (dbUri.getUserInfo() != null) {
                    String[] userInfo = dbUri.getUserInfo().split(":");
                    username = userInfo[0];
                    password = userInfo.length > 1 ? userInfo[1] : "";
                } else {
                    // If no credentials in URL, Railway might provide them separately
                    username = pgUser != null ? pgUser : "postgres";
                    password = pgPassword != null ? pgPassword : "";
                }
            }

            System.out.println("Connecting to PostgreSQL with JDBC URL: " + jdbcUrl);
            System.out.println("Username: " + username);

            return DataSourceBuilder.create()
                    .driverClassName("org.postgresql.Driver")
                    .url(jdbcUrl)
                    .username(username)
                    .password(password)
                    .build();

        } catch (Exception e) {
            System.err.println("Failed to configure DataSource. DATABASE_URL: " + databaseUrl);
            System.err.println("PGUSER: " + pgUser);
            System.err.println("PGHOST: " + pgHost);
            System.err.println("PGDATABASE: " + pgDatabase);
            throw new RuntimeException("Failed to parse DATABASE_URL: " + databaseUrl, e);
        }
    }
}
