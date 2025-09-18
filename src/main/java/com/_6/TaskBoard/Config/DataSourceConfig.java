package com._6.TaskBoard.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Bean
    @Primary
    public DataSource dataSource() {
        String jdbcUrl;
        
        if (databaseUrl == null || databaseUrl.isEmpty()) {
            // Use default local database
            jdbcUrl = "jdbc:postgresql://localhost:5432/taskboard?sslmode=require";
        } else {
            // Remove jdbc: prefix if it exists, then add it back
            String cleanUrl = databaseUrl.startsWith("jdbc:") ? 
                databaseUrl.substring(5) : databaseUrl;
            jdbcUrl = "jdbc:" + cleanUrl;
        }
        
        System.out.println("=== DATABASE CONFIGURATION ===");
        System.out.println("Original DATABASE_URL: " + databaseUrl);
        System.out.println("Final JDBC URL: " + jdbcUrl);
        System.out.println("==============================");
        
        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}