package com.adriannebulao.tasktracker.security.presentation;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(
        @NotBlank String username,
        @NotBlank String password) {}
