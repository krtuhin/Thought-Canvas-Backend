package com.rootapp.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rootapp.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // handling resource not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {

        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    // handling hibernate validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException ex) {

        Map<String, String> response = new HashMap<>();

        response.put("success", "false");

        // fetch error and field name and add to map
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fiendName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            response.put(fiendName, message);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
