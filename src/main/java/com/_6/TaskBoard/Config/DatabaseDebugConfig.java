package com._6.TaskBoard.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
public class DatabaseDebugConfig {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Value("${SPRING_DATASOURCE_URL:}")
    private String springDatasourceUrl;

    @Value("${DATABASE_USERNAME:}")
    private String databaseUsername;

    @Value("${DATABASE_PASSWORD:}")
    private String databasePassword;

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        System.out.println("=== DATABASE CONFIGURATION DEBUG ===");
        System.out.println("DATABASE_URL: " + (databaseUrl.isEmpty() ? "NOT SET" : databaseUrl));
        System.out.println("SPRING_DATASOURCE_URL: " + (springDatasourceUrl.isEmpty() ? "NOT SET" : springDatasourceUrl));
        System.out.println("DATABASE_USERNAME: " + (databaseUsername.isEmpty() ? "NOT SET" : databaseUsername));
        System.out.println("DATABASE_PASSWORD: " + (databasePassword.isEmpty() ? "NOT SET" : "***HIDDEN***"));
        System.out.println("=====================================");
    }
}

