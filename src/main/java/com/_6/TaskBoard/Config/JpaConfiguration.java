package com._6.TaskBoard.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com._6.TaskBoard.Repository")
@EnableTransactionManagement
public class JpaConfiguration {
    // JPA configuration is handled by Spring Boot auto-configuration
}
