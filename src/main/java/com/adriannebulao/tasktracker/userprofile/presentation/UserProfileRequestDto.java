package com.adriannebulao.tasktracker.userprofile.presentation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserProfileRequestDto(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull Long accountId) {}
