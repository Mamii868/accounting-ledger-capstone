package com.pluralsight.pennywise.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Turns common errors into JSON bodies so clients can tell
 * WHY a request failed instead of getting a bare status code.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid failures -> 400 with a field -> message map
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fields = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            fields.put(fe.getField(), fe.getDefaultMessage());
        }
        return Map.of("error", "Validation failed", "fields", fields);
    }

    // unparseable / malformed JSON body -> 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUnreadable(HttpMessageNotReadableException ex) {
        return Map.of("error", "Malformed request body: " + ex.getMostSpecificCause().getMessage());
    }

    // bad path variable / query param (e.g. invalid UUID) -> 400
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return Map.of("error", "Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'");
    }

    // DB constraint violations (duplicates, broken foreign keys) -> 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleIntegrity(DataIntegrityViolationException ex) {
        return Map.of("error", "Request conflicts with existing data (duplicate value or invalid reference)");
    }
}
