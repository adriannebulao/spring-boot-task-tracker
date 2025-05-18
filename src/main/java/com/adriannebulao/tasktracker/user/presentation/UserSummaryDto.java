package com.adriannebulao.tasktracker.user.presentation;

public record UserSummaryDto(
        Long id,
        String firstName,
        String lastName,
        String imageUrl) {}
