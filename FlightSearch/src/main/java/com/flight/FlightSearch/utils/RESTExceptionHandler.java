package com.flight.FlightSearch.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
public class RESTExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgument(IllegalArgumentException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), "400");
        return new ResponseEntity<>(response, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> noSuchElement(NoSuchElementException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), "404");
        return new ResponseEntity<>(response, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValid(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        List<String> errors = new ArrayList<>();
        allErrors.forEach(error -> {
            errors.add(error.getDefaultMessage());
        });
        ErrorResponse response = new ErrorResponse(errors.toString(), "400");
        return new ResponseEntity<>(response, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), "500");
        return new ResponseEntity<>(response, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Setter
    @Getter
    static public class ErrorResponse {

        ErrorResponse(String message, String code) {
            this.message = message;
            this.code = code;
            this.timestamp = LocalDateTime.now();
        }

        private final LocalDateTime timestamp;
        private final String message;
        private final String code;
    }
}
