package com.adriannebulao.tasktracker.security.presentation;

public record LoginRequestDto(
        String username,
        String password) {}
