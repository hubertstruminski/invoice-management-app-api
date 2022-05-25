package com.invoice.management.app.exception;

import com.invoice.management.app.payload.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IllegalUUIDException extends RuntimeException {
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<Error> handleResourceNotFoundException(
//            ResourceNotFoundException exception, WebRequest webRequest) {
//        Error error = new Error(new Date(), exception.getMessage(),
//                webRequest.getDescription(false));
//
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }
}
