package com.computa.books.controller;

import com.computa.books.model.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler for handling the exception across the whole application
 * This class provides methods for handle specific exceptions and return appropriate HTTP ressponses
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles MethodArgumentNotValidException and returns a map containing the status and the validation errors
     * @param exception the exception thrown when method arguments are not valid
     * @return a map containing the HTTP status and validation errors
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, String> handleInvalidResourceException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.toString());

        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return errors;
    }

    /**
     * Handles ResourceNotFoundException and returns a map containing the status and the validation errors
     * @param exception the exception thrown when a request resource is not found
     * @return a map containing the HTTP status and the exception message
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public Map<String, String> handleNoSuchElementException(ResourceNotFoundException exception) {
        Map<String, String> errors = new HashMap<>();

        errors.put("status", HttpStatus.NOT_FOUND.toString());
        errors.put("message", exception.getMessage());

        return errors;
    }

}
