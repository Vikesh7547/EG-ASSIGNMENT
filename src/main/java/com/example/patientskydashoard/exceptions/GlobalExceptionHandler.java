package com.example.patientskydashoard.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.*;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFoundHandler(ResourceNotFoundException ex){
        Map map = new HashMap();
        map.put("message", ex.getMessage());
        map.put("success", false);
        map.put("status", "Custom message from");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }
}