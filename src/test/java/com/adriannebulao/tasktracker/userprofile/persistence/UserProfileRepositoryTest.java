package com.adriannebulao.tasktracker.userprofile.persistence;

import com.adriannebulao.tasktracker.common.exception.UserProfileNotFoundException;
import com.adriannebulao.tasktracker.userprofile.domain.UserProfile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.adriannebulao.tasktracker.util.TestDataFactory.createTestUserProfile;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserProfileRepositoryTest {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Test
    public void save_shouldSaveUserProfile_whenUserProfileExists() {
        UserProfile userProfile = createTestUserProfile();

        UserProfile savedUserProfile = userProfileRepository.save(userProfile);

        Assertions.assertThat(savedUserProfile).isNotNull();
        Assertions.assertThat(savedUserProfile.getId()).isGreaterThan(0);
        Assertions.assertThat(savedUserProfile.getFirstName()).isEqualTo(userProfile.getFirstName());
        Assertions.assertThat(savedUserProfile.getLastName()).isEqualTo(userProfile.getLastName());
        Assertions.assertThat(savedUserProfile.getImageUrl()).isEqualTo(userProfile.getImageUrl());
    }

    @Test
    public void findAll_shouldReturnAllUserProfiles_whenThereAreUserProfiles() {
        UserProfile userProfileOne = createTestUserProfile();
        UserProfile userProfileTwo = createTestUserProfile();

        userProfileRepository.save(userProfileOne);
        userProfileRepository.save(userProfileTwo);

        List<UserProfile> userProfiles = userProfileRepository.findAll();

        Assertions.assertThat(userProfiles).isNotNull();
        Assertions.assertThat(userProfiles.size()).isEqualTo(2);
    }

    @Test
    public void findById_shouldReturnUserProfile_whenUserProfileExists() {
        UserProfile userProfile = createTestUserProfile();

        userProfileRepository.save(userProfile);

        UserProfile foundUserProfile = userProfileRepository.findById(userProfile.getId()).get();

        Assertions.assertThat(foundUserProfile).isNotNull();
    }

    @Test
    public void findById_shouldThrowUserProfileNotFoundException_whenUserProfileNotFound() {
        Long userProfileId = 1L;

        Assertions.assertThatThrownBy(() -> {
            userProfileRepository.findById(userProfileId).orElseThrow(() -> new UserProfileNotFoundException("User profile not found"));
        })
                .isInstanceOf(UserProfileNotFoundException.class)
                .hasMessageContaining("User profile not found");
    }

    @Test
    public void update_shouldUpdateUserProfile_whenUserProfileExists() {
        UserProfile userProfile = createTestUserProfile();

        UserProfile savedUserProfile = userProfileRepository.save(userProfile);

        savedUserProfile.setFirstName("Updated");
        savedUserProfile.setLastName("Updated");
        savedUserProfile.setImageUrl("Updated");

        UserProfile updatedUserProfile = userProfileRepository.save(savedUserProfile);

        Assertions.assertThat(updatedUserProfile).isNotNull();
        Assertions.assertThat(updatedUserProfile.getId()).isEqualTo(savedUserProfile.getId());
        Assertions.assertThat(updatedUserProfile.getFirstName()).isEqualTo("Updated");
        Assertions.assertThat(updatedUserProfile.getLastName()).isEqualTo("Updated");
        Assertions.assertThat(updatedUserProfile.getImageUrl()).isEqualTo("Updated");
    }

    @Test
    public void delete_shouldDeleteUserProfile_whenUserProfileExists() {
        UserProfile userProfile = createTestUserProfile();

        userProfileRepository.save(userProfile);
        userProfileRepository.delete(userProfile);

        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfile.getId());

        Assertions.assertThat(userProfileOptional).isEmpty();
    }
}