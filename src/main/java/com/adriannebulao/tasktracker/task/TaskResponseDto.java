package com.adriannebulao.tasktracker.task;

record TaskResponseDto(
        Integer id,
        String name,
        TaskStatus status,
        TaskPriority priority) {}
