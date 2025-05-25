package com.adriannebulao.tasktracker.userprofile.application;

import com.adriannebulao.tasktracker.common.exception.UserProfileNotFoundException;
import com.adriannebulao.tasktracker.userprofile.domain.UserProfile;
import com.adriannebulao.tasktracker.userprofile.persistence.UserProfileRepository;
import com.adriannebulao.tasktracker.userprofile.presentation.UserProfileMapper;
import com.adriannebulao.tasktracker.userprofile.presentation.UserProfileRequestDto;
import com.adriannebulao.tasktracker.userprofile.presentation.UserProfileResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.adriannebulao.tasktracker.util.TestDataFactory.createTestUserProfile;
import static com.adriannebulao.tasktracker.util.TestDataFactory.createTestUserProfileRequestDto;
import static com.adriannebulao.tasktracker.util.TestDataFactory.createTestUserProfileResponseDto;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTest {

    @Mock private UserProfileRepository userProfileRepository;
    @Mock private UserProfileMapper userProfileMapper;
    @InjectMocks private UserProfileService userProfileService;

    @Test
    public void createUserProfile_ShouldReturnUserProfileResponseDto_WhenUserProfileIsCreated() {
        UserProfile userProfile = createTestUserProfile();
        UserProfileRequestDto userProfileRequestDto = createTestUserProfileRequestDto();
        UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(
                userProfile.getId(),
                userProfile.getFirstName(),
                userProfile.getLastName(),
                userProfile.getImageUrl(),
                null
        );

        when(userProfileMapper.toEntity(userProfileRequestDto)).thenReturn(userProfile);
        when(userProfileRepository.save(Mockito.any(UserProfile.class))).thenReturn(userProfile);
        when(userProfileMapper.toDto(userProfile)).thenReturn(userProfileResponseDto);

        UserProfileResponseDto savedUserProfile = userProfileService.createUserProfile(userProfileRequestDto);

        Assertions.assertThat(savedUserProfile).isNotNull();
        Assertions.assertThat(savedUserProfile.id()).isEqualTo(userProfile.getId());
        Assertions.assertThat(savedUserProfile.firstName()).isEqualTo(userProfile.getFirstName());
        Assertions.assertThat(savedUserProfile.lastName()).isEqualTo(userProfile.getLastName());
        Assertions.assertThat(savedUserProfile.imageUrl()).isEqualTo(userProfile.getImageUrl());
        Assertions.assertThat(savedUserProfile.tasks()).isNull();
    }

    @Test
    public void getAllUserProfiles_ShouldReturnAllUserProfiles_WhenUserProfilesAreCreated() {
        UserProfile userProfile1 = createTestUserProfile();
        UserProfile userProfile2 = createTestUserProfile();
        List<UserProfile> users = List.of(userProfile1, userProfile2);

        UserProfileResponseDto userProfileResponseDto1 = createTestUserProfileResponseDto(userProfile1.getId());
        UserProfileResponseDto userProfileResponseDto2 = createTestUserProfileResponseDto(userProfile2.getId());
        List<UserProfileResponseDto> userResponseProfileDtos = List.of(userProfileResponseDto1, userProfileResponseDto2);

        when(userProfileRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(users);
        when(userProfileMapper.toDto(userProfile1)).thenReturn(userProfileResponseDto1);
        when(userProfileMapper.toDto(userProfile2)).thenReturn(userProfileResponseDto2);

        List<UserProfileResponseDto> allUsers = userProfileService.getAllUserProfiles();

        Assertions.assertThat(allUsers).isEqualTo(userResponseProfileDtos);
    }

    @Test
    public void getUserProfileById_ShouldReturnUserProfileResponseDto_WhenUserProfileIsFound() {
        UserProfile userProfile = createTestUserProfile();
        UserProfileResponseDto userProfileResponseDto = createTestUserProfileResponseDto(userProfile.getId());

        when(userProfileRepository.findById(userProfile.getId())).thenReturn(Optional.of(userProfile));
        when(userProfileMapper.toDto(userProfile)).thenReturn(userProfileResponseDto);

        UserProfileResponseDto savedUserProfile = userProfileService.getUserProfileById(userProfile.getId());

        Assertions.assertThat(savedUserProfile).isNotNull();
        Assertions.assertThat(savedUserProfile).isEqualTo(userProfileResponseDto);
    }

    @Test
    public void getUserProfileById_ShouldReturnUserProfileResponseDto_WhenUserProfileIsNotFound() {
        Long userProfileId = 1L;

        when(userProfileRepository.findById(userProfileId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userProfileService.getUserProfileById(userProfileId))
                .isInstanceOf(UserProfileNotFoundException.class)
                .hasMessageContaining("User profile not found");
    }

    @Test
    public void updateUserProfile_ShouldUpdateUserProfileResponseDto_WhenUserProfileIsUpdated() {
        UserProfile userProfile = createTestUserProfile();
        UserProfileRequestDto userProfileRequestDto = createTestUserProfileRequestDto();

        UserProfile updatedUserProfile = UserProfile.builder()
                .id(userProfile.getId())
                .firstName("Updated")
                .lastName("Updated")
                .imageUrl("Updated")
                .build();

        UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(
                updatedUserProfile.getId(),
                updatedUserProfile.getFirstName(),
                updatedUserProfile.getLastName(),
                updatedUserProfile.getImageUrl(),
                null
        );

        when(userProfileRepository.findById(userProfile.getId())).thenReturn(Optional.of(userProfile));
        when(userProfileRepository.save(userProfile)).thenReturn(updatedUserProfile);
        when(userProfileMapper.toDto(updatedUserProfile)).thenReturn(userProfileResponseDto);

        UserProfileResponseDto updatedUserProfileDto = userProfileService.updateUserProfile(userProfile.getId(), userProfileRequestDto);

        Assertions.assertThat(updatedUserProfileDto).isNotNull();
        Assertions.assertThat(updatedUserProfileDto).isEqualTo(userProfileResponseDto);

        Mockito.verify(userProfileMapper).updateUserProfileFromDto(userProfileRequestDto, userProfile);
    }

    @Test
    public void deleteUserProfile_ShouldDeleteUserProfileResponseDto_WhenUserProfileIsDeleted() {
        UserProfile userProfile = createTestUserProfile();

        when(userProfileRepository.findById(userProfile.getId())).thenReturn(Optional.of(userProfile));

        userProfileService.deleteUserProfile(userProfile.getId());

        Mockito.verify(userProfileRepository).delete(userProfile);
    }
}
