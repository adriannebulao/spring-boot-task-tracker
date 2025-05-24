package com.adriannebulao.tasktracker.security.presentation;

import java.util.Set;

public record RegisterResponseDto(
        Long id,
        String username,
        Set<String> roles,
        String message
) {}
