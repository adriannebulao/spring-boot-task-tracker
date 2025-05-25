package com.adriannebulao.tasktracker.util;

import com.adriannebulao.tasktracker.userprofile.domain.UserProfile;
import com.adriannebulao.tasktracker.userprofile.presentation.UserProfileRequestDto;
import com.adriannebulao.tasktracker.userprofile.presentation.UserProfileResponseDto;

public class TestDataFactory {

    public static UserProfile createTestUserProfile() {
        return UserProfile.builder()
                .firstName("Test")
                .lastName("Test")
                .imageUrl("Test")
                .build();
    }

    public static UserProfileRequestDto createTestUserProfileRequestDto() {
        return new UserProfileRequestDto(
                "Test",
                "Test",
                "Test"
        );
    }

    public static UserProfileResponseDto createTestUserProfileResponseDto(Long id) {
        return new UserProfileResponseDto(
                id,
                "Test",
                "Test",
                "Test",
                null
        );
    }
}
