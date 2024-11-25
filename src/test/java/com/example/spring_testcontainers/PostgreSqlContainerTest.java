package com.example.spring_testcontainers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Работает!
 */
class PostgreSqlContainerTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgis/postgis:15-3.4-alpine")
                    .asCompatibleSubstituteFor("postgres")
    );
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
        System.out.println("Database: " + postgres.getDatabaseName());
        System.out.println("Username: " + postgres.getUsername());
        System.out.println("Password: " + postgres.getPassword());
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        DataSource dataSource = new DriverManagerDataSource(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        jdbcTemplate = new JdbcTemplate(dataSource);

        // Создание таблицы
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL
                )
        """);

        // Очистка таблицы перед каждым тестом
        jdbcTemplate.execute("TRUNCATE TABLE users");

        // Добавление данных
        jdbcTemplate.update("INSERT INTO users (name) VALUES (?)", "Alice");
        jdbcTemplate.update("INSERT INTO users (name) VALUES (?)", "Bob");
    }

    @Test
    void shouldGetUsers() {
        // Проверка данных
        Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
        assertEquals(2, userCount);

        String userName = jdbcTemplate.queryForObject("SELECT name FROM users WHERE id = 1", String.class);
        assertEquals("Alice", userName);
    }


}