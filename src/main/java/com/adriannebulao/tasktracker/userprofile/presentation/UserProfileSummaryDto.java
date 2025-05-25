package com.adriannebulao.tasktracker.userprofile.presentation;

public record UserProfileSummaryDto(
        Long id,
        String firstName,
        String lastName,
        String imageUrl) {}
