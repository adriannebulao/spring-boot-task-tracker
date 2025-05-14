package com.adriannebulao.tasktracker.task;

record TaskRequestDto(
        String name,
        TaskStatus status,
        TaskPriority priority) {}
