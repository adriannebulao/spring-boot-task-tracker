package com.adriannebulao.tasktracker.task.application;

import com.adriannebulao.tasktracker.task.domain.Task;
import com.adriannebulao.tasktracker.task.domain.TaskNotFoundException;
import com.adriannebulao.tasktracker.task.persistence.TaskRepository;
import com.adriannebulao.tasktracker.task.presentation.TaskMapper;
import com.adriannebulao.tasktracker.task.presentation.TaskRequestDto;
import com.adriannebulao.tasktracker.task.presentation.TaskResponseDto;
import com.adriannebulao.tasktracker.user.domain.User;
import com.adriannebulao.tasktracker.common.exception.UserNotFoundException;
import com.adriannebulao.tasktracker.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        Task task = taskMapper.toEntity(taskRequestDto);
        User user = userRepository.findById(taskRequestDto.userId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        task.setUser(user);
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

        if (taskRequestDto.userId() != null) {
            User user = userRepository.findById(taskRequestDto.userId())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            task.setUser(user);
        }

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
    }
}