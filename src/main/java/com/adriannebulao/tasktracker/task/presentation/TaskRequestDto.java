package com.adriannebulao.tasktracker.task.presentation;

import com.adriannebulao.tasktracker.task.domain.TaskPriority;
import com.adriannebulao.tasktracker.task.domain.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequestDto(
        @NotBlank String name,
        @NotNull TaskStatus status,
        @NotNull TaskPriority priority,
        @NotNull Long userId) {}
