package com.adriannebulao.tasktracker.task.presentation;

import com.adriannebulao.tasktracker.task.domain.TaskPriority;
import com.adriannebulao.tasktracker.task.domain.TaskStatus;
import com.adriannebulao.tasktracker.userprofile.presentation.UserProfileSummaryDto;

public record TaskResponseDto(
        Long id,
        String name,
        TaskStatus status,
        TaskPriority priority,
        UserProfileSummaryDto userProfile) {}
