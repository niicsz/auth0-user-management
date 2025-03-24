package com.usermanagement.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan("com.usermanagement.infrastructure.persistence.entity")
@EnableJpaRepositories("com.usermanagement.infrastructure.persistence.repository")
public class DatabaseConfig {

}