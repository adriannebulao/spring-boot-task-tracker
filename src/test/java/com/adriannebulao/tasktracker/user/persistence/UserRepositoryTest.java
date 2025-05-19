package com.adriannebulao.tasktracker.user.persistence;

import com.adriannebulao.tasktracker.common.exception.UserNotFoundException;
import com.adriannebulao.tasktracker.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.adriannebulao.tasktracker.util.TestDataFactory.createTestUser;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save_shouldSaveUser_whenUserExists() {
        User user = createTestUser();

        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
        Assertions.assertThat(savedUser.getFirstName()).isEqualTo(user.getFirstName());
        Assertions.assertThat(savedUser.getLastName()).isEqualTo(user.getLastName());
        Assertions.assertThat(savedUser.getImageUrl()).isEqualTo(user.getImageUrl());
    }

    @Test
    public void findAll_shouldReturnAllUsers_whenThereAreUsers() {
        User userOne = createTestUser();
        User userTwo = createTestUser();

        userRepository.save(userOne);
        userRepository.save(userTwo);

        List<User> users = userRepository.findAll();

        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.size()).isEqualTo(2);
    }

    @Test
    public void findById_shouldReturnUser_whenUserExists() {
        User user = createTestUser();

        userRepository.save(user);

        User foundUser = userRepository.findById(user.getId()).get();

        Assertions.assertThat(foundUser).isNotNull();
    }

    @Test
    public void findById_shouldThrowUserNotFoundException_whenUserNotFound() {
        Long userId = 1L;

        Assertions.assertThatThrownBy(() -> {
            userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        })
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    public void update_shouldUpdateUser_whenUserExists() {
        User user = createTestUser();

        User savedUser = userRepository.save(user);

        savedUser.setFirstName("Updated");
        savedUser.setLastName("Updated");
        savedUser.setImageUrl("Updated");

        User updatedUser = userRepository.save(savedUser);

        Assertions.assertThat(updatedUser).isNotNull();
        Assertions.assertThat(updatedUser.getId()).isEqualTo(savedUser.getId());
        Assertions.assertThat(updatedUser.getFirstName()).isEqualTo("Updated");
        Assertions.assertThat(updatedUser.getLastName()).isEqualTo("Updated");
        Assertions.assertThat(updatedUser.getImageUrl()).isEqualTo("Updated");
    }

    @Test
    public void delete_shouldDeleteUser_whenUserExists() {
        User user = createTestUser();

        userRepository.save(user);
        userRepository.delete(user);

        Optional<User> userOptional = userRepository.findById(user.getId());

        Assertions.assertThat(userOptional).isEmpty();
    }
}