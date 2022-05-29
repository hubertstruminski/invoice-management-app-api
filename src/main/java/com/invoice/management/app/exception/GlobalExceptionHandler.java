package com.invoice.management.app.exception;

import com.invoice.management.app.payload.Error;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handleResourceNotFoundException(
            ResourceNotFoundException exception, WebRequest webRequest) {
        Error error = new Error(new Date(), exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Error> handleApiException(
            ApiException exception, WebRequest webRequest) {
        Error error = new Error(new Date(), exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGlobalException(
            Exception exception, WebRequest webRequest) {
        Error error = new Error(new Date(), exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        Error error = new Error(new Date(), message, request.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
