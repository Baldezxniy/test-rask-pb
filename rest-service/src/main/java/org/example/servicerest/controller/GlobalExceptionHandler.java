package org.example.servicerest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleEntityNotFoundException(RuntimeException exception) {
        return ResponseEntity.status(500).body(exception.getMessage());
    }

}
