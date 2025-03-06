package com.magalu.infrastructure.exceptions;

import com.magalu.domain.validation.Error;
import com.magalu.infrastructure.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(Exception ex) {
        var route = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();

        List<Error> errors = new ArrayList<>();
        errors.add(new Error("Error", ex.getMessage()));

        var response = ApiResponse.createFailed(
                "MethodArgumentNotValidException",
                errors,
                HttpStatus.BAD_REQUEST.value(),
                route);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(Exception ex) {
        var route = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();

        List<Error> errors = new ArrayList<>();
        errors.add(new Error("Error", ex.getMessage()));

        var response = ApiResponse.createFailed(
                "MethodArgumentTypeMismatchException",
                errors,
                HttpStatus.BAD_REQUEST.value(),
                route);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(FormatException.class)
    public ResponseEntity<?> handleFormatException(FormatException ex) {
        var route = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();

        var response = ApiResponse.createFailed(
                "FormatException",
                ex.errors,
                HttpStatus.BAD_REQUEST.value(),
                route);

        return ResponseEntity.badRequest().body(response);
    }
}
