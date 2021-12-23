package com.epam.esm.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"com.epam.esm.app",
        "com.epam.esm.service",
        "com.epam.esm.repository"})
@PropertySource("classpath:application.properties")
@AllArgsConstructor
public class AppConfig {

    private final Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("datasource.driver_class"));
        dataSource.setUrl(env.getRequiredProperty("datasource.connection.url"));
        dataSource.setUsername(env.getRequiredProperty("datasource.connection.username"));
        dataSource.setPassword(env.getRequiredProperty("datasource.connection.password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
