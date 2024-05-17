package com.flight.FlightSearch.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class RESTExceptionHandler {
//TODO test
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), "500");
        return new ResponseEntity<>(response, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    static class ErrorResponse {

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
