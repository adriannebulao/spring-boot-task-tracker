package com.adriannebulao.tasktracker.task.presentation;

import com.adriannebulao.tasktracker.task.domain.TaskPriority;
import com.adriannebulao.tasktracker.task.domain.TaskStatus;

public record TaskSummaryDto(
        Long id,
        String name,
        TaskStatus status,
        TaskPriority priority) {}
