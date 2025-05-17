package com.adriannebulao.tasktracker.user.presentation;

public record UserRequestDto(
        String firstName,
        String lastName,
        String imageUrl,
        String email,
        String password) {}
