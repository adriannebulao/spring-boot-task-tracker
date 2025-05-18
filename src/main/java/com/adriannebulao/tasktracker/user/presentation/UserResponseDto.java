package com.adriannebulao.tasktracker.user.presentation;

import com.adriannebulao.tasktracker.task.presentation.TaskSummaryDto;

import java.util.List;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String imageUrl,
        List<TaskSummaryDto> tasks) {}
