package com.prosoft.testcontainer_demo;

import com.prosoft.testcontainer_demo.entity.User;
import com.prosoft.testcontainer_demo.repository.UserRepository;
import com.prosoft.testcontainer_demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.sql.init.mode=never"
})
class UserServiceTest {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    // Create
    @Test
    @DisplayName("Сохраняет пользователя и возвращает запись с id")
    void shouldCreateUser() {
        User saved = userService.createUser(new User("Alice", "alice@example.com"));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Alice");
        assertThat(saved.getEmail()).isEqualTo("alice@example.com");
    }


    // Read
    @Test
    @DisplayName("Возвращает всех сохраненных пользователей")
    void shouldGetAllUsers() {

        userService.createUser(new User("Alice", "alice@example.com"));
        userService.createUser(new User("Bob", "bob@example.com"));

        List<User> users = userService.getAllUsers();

        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getEmail)
                .containsExactlyInAnyOrder("alice@example.com", "bob@example.com");

    }

    // Update
    @Test
    @DisplayName("Обновляет имя и email")
    void shouldUpdateUser() {
        User saved = userService.createUser(new User("Alice", "alice@example.com"));

        Optional<User> updated = userService.updateUser(saved.getId(), new User("Alice Updated", "alice.new@example.com"));

        assertThat(updated.get().getName()).isEqualTo("Alice Updated");
        assertThat(updated.get().getEmail()).isEqualTo("alice.new@example.com");

    }

    // Delete
    @Test
    @DisplayName("Удаляет пользователя")
    void shouldDeleteUser() {
        User saved = userService.createUser(new User("Alice", "alice@example.com"));

        userService.deleteUser(saved.getId());

        assertThat(userService.getUserById(saved.getId())).isEmpty();

    }


}
