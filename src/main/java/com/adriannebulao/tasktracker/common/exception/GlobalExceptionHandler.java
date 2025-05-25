package com.adriannebulao.tasktracker.common.exception;

import com.adriannebulao.tasktracker.task.domain.TaskNotFoundException;
import com.adriannebulao.tasktracker.userprofile.domain.UserAccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAccountNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserAccountNotFound(UserAccountNotFoundException e) {
        return buildNotFoundResponse(e.getMessage());
    }

    @ExceptionHandler(UserProfileNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserProfileNotFound(UserProfileNotFoundException e) {
        return buildNotFoundResponse(e.getMessage());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTaskNotFound(TaskNotFoundException e) {
        return buildNotFoundResponse(e.getMessage());
    }

    public ResponseEntity<Map<String, String>> buildNotFoundResponse(String message) {
        Map<String, String> body = new HashMap<>();
        body.put("error", message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
