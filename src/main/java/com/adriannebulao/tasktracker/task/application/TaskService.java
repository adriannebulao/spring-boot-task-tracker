package com.adriannebulao.tasktracker.task.application;

import com.adriannebulao.tasktracker.task.domain.Task;
import com.adriannebulao.tasktracker.task.domain.TaskNotFoundException;
import com.adriannebulao.tasktracker.task.persistence.TaskRepository;
import com.adriannebulao.tasktracker.task.presentation.TaskMapper;
import com.adriannebulao.tasktracker.task.presentation.TaskRequestDto;
import com.adriannebulao.tasktracker.task.presentation.TaskResponseDto;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        Task task = taskMapper.toEntity(taskRequestDto);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    public TaskResponseDto getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        return taskMapper.toDto(task);
    }

    public TaskResponseDto updateTask(Long id, TaskRequestDto taskRequestDto) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskMapper.updateTaskFromDto(taskRequestDto, task);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
    }
}