package com._6.TaskBoard.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Bean
    @Primary
    public DataSource dataSource() {
        // Handle Cloud Render DATABASE_URL format
        String jdbcUrl = databaseUrl;
        
        // If DATABASE_URL doesn't start with jdbc:, add it
        if (databaseUrl != null && !databaseUrl.isEmpty() && !databaseUrl.startsWith("jdbc:")) {
            jdbcUrl = "jdbc:" + databaseUrl;
        }
        
        // If DATABASE_URL is empty, use default local database
        if (databaseUrl == null || databaseUrl.isEmpty()) {
            jdbcUrl = "jdbc:postgresql://localhost:5432/taskboard?sslmode=require";
        }
        
        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
