package com.adriannebulao.tasktracker.userprofile.presentation;

public record UserProfileRequestDto(
        String firstName,
        String lastName,
        String imageUrl,
        Long accountId) {}
