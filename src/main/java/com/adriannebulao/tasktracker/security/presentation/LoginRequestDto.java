package com.adriannebulao.tasktracker.security.presentation;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank String username,
        @NotBlank String password) {}
