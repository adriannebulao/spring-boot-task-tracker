package com.adriannebulao.tasktracker.user.presentation;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String imageUrl
) {}
