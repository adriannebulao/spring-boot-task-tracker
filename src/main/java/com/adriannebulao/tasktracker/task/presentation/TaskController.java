package com.adriannebulao.tasktracker.task.presentation;

import com.adriannebulao.tasktracker.task.application.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto task = taskService.createTask(taskRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        List<TaskResponseDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        TaskResponseDto task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id,
                                                      @RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto task = taskService.updateTask(id, taskRequestDto);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
