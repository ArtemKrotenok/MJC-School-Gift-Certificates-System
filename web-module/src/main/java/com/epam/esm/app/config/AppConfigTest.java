package com.epam.esm.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.epam.esm.app",
        "com.epam.esm.service",
        "com.epam.esm.repository"})
@PropertySource("classpath:application.properties")
@AllArgsConstructor
@Profile("test")
public class AppConfigTest implements WebMvcConfigurer {

    public static final String ENCODING = "UTF-8";
    public static final String SCHEMA_DB_SQL = "schemaDB.sql";
    public static final String TEST_DATA_DB_SQL = "testDataDB.sql";

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding(ENCODING)
                .ignoreFailedDrops(true)
                .addScript(SCHEMA_DB_SQL)
                .addScript(TEST_DATA_DB_SQL)
                .build();
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
