package com.adriannebulao.tasktracker.security.presentation;

public record AuthResponseDto(
        String accessToken,
        String tokenType) {}
