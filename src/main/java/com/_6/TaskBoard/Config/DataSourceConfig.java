package com._6.TaskBoard.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DataSourceConfig {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Bean
    @Primary
    public DataSource dataSource() {
        String jdbcUrl;
        String username = null;
        String password = null;

        if (databaseUrl == null || databaseUrl.isEmpty()) {
            // Use default local database
            jdbcUrl = "jdbc:postgresql://localhost:5432/taskboard?sslmode=require";
        } else {
            try {
                // Strip optional leading jdbc:
                String raw = databaseUrl.startsWith("jdbc:") ? databaseUrl.substring(5) : databaseUrl;

                // Render often supplies postgres://...; JDBC expects postgresql://...
                if (raw.startsWith("postgres://")) {
                    raw = "postgresql://" + raw.substring("postgres://".length());
                }

                URI uri = new URI(raw);

                // Extract credentials from URI userInfo if present
                if (uri.getUserInfo() != null && !uri.getUserInfo().isEmpty()) {
                    String[] parts = uri.getUserInfo().split(":", 2);
                    username = parts.length > 0 ? parts[0] : null;
                    password = parts.length > 1 ? parts[1] : null;
                }

                String host = uri.getHost();
                int port = uri.getPort();
                String path = uri.getPath();
                String query = uri.getQuery();

                StringBuilder urlBuilder = new StringBuilder("jdbc:postgresql://");
                urlBuilder.append(host != null ? host : "localhost");
                if (port > -1) {
                    urlBuilder.append(":" ).append(port);
                }
                // Ensure path (database name) starts with '/'
                if (path == null || path.isEmpty()) {
                    path = "/taskboard";
                }
                urlBuilder.append(path);
                if (query != null && !query.isEmpty()) {
                    urlBuilder.append("?").append(query);
                }

                jdbcUrl = urlBuilder.toString();
            } catch (URISyntaxException e) {
                // Fallback: best-effort conversion by simply forcing jdbc:postgresql prefix
                String cleanUrl = databaseUrl.replaceFirst("^jdbc:", "");
                cleanUrl = cleanUrl.replaceFirst("^postgres://", "postgresql://");
                if (!cleanUrl.startsWith("postgresql://")) {
                    cleanUrl = "postgresql://" + cleanUrl;
                }
                jdbcUrl = "jdbc:" + cleanUrl;
            }
        }

        System.out.println("=== DATABASE CONFIGURATION ===");
        System.out.println("Original DATABASE_URL: " + databaseUrl);
        System.out.println("Final JDBC URL: " + jdbcUrl);
        System.out.println("Username from URL: " + (username != null));
        System.out.println("==============================");

        DataSourceBuilder<?> builder = DataSourceBuilder.create()
                .url(jdbcUrl)
                .driverClassName("org.postgresql.Driver");

        if (username != null) {
            builder.username(username);
        }
        if (password != null) {
            builder.password(password);
        }

        return builder.build();
    }
}