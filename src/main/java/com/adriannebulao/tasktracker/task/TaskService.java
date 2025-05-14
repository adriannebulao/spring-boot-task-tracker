package com.adriannebulao.tasktracker.task;

import org.springframework.stereotype.Service;

@Service
class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        Task task = new Task(taskRequestDto.name(), taskRequestDto.status(), taskRequestDto.priority());
        task = taskRepository.save(task);
        return new TaskResponseDto(task.getId(), task.getName(), task.getStatus(), task.getPriority());
    }
}
