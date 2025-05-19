package com.adriannebulao.tasktracker.util;

import com.adriannebulao.tasktracker.user.domain.User;
import com.adriannebulao.tasktracker.user.presentation.UserRequestDto;
import com.adriannebulao.tasktracker.user.presentation.UserResponseDto;

public class TestDataFactory {

    public static User createTestUser() {
        return User.builder()
                .firstName("Test")
                .lastName("Test")
                .imageUrl("Test")
                .build();
    }

    public static UserRequestDto createTestUserRequestDto() {
        return new UserRequestDto(
                "Test",
                "Test",
                "Test"
        );
    }

    public static UserResponseDto createTestUserResponseDto(Long id) {
        return new UserResponseDto(
                id,
                "Test",
                "Test",
                "Test",
                null
        );
    }
}
