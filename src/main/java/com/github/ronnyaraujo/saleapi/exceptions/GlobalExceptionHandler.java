package com.github.ronnyaraujo.saleapi.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<MessageError> objectNotFound(ObjectNotFoundException e){
        MessageError messageError = new MessageError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MessageError> DataIntegrityException(DataIntegrityViolationException e){
        MessageError messageError = new MessageError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError);
    }
}
