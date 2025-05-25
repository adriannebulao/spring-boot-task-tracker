package com.adriannebulao.tasktracker.common.exception;

import com.adriannebulao.tasktracker.task.domain.TaskNotFoundException;
import com.adriannebulao.tasktracker.userprofile.domain.UserAccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGenericException(Exception e) {
        return Map.of("error", "An unexpected error occurred",
                "message", e.getMessage());
    }
}
