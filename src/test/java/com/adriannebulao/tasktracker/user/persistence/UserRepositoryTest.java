package com.adriannebulao.tasktracker.user.persistence;

import com.adriannebulao.tasktracker.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save_shouldSaveUser() {
        User user = User.builder()
                .firstName("Test")
                .lastName("Test")
                .imageUrl("Test")
                .build();

        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
        Assertions.assertThat(savedUser.getFirstName()).isEqualTo(user.getFirstName());
        Assertions.assertThat(savedUser.getLastName()).isEqualTo(user.getLastName());
        Assertions.assertThat(savedUser.getImageUrl()).isEqualTo(user.getImageUrl());
    }
}
