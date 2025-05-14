package com.adriannebulao.tasktracker.task;

import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(task -> new TaskResponseDto(
                        task.getId(),
                        task.getName(),
                        task.getStatus(),
                        task.getPriority()
                ))
                .toList();
    }

    public TaskResponseDto getTaskById(Integer id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        return new TaskResponseDto(task.getId(), task.getName(), task.getStatus(), task.getPriority());
    }
}
