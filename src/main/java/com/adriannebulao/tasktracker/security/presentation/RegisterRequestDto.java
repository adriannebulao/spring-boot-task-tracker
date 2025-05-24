package com.adriannebulao.tasktracker.security.presentation;

public record RegisterRequestDto(
        String username,
        String password) {}
