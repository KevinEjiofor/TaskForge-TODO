package com.taskForge.exceptions;

import com.taskForge.dto.Respond.ApiRespond;


import com.taskForge.dto.Respond.ExceptionsRespond;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Object> handleExceptions(TaskNotFoundException ex, WebRequest request) {
        ExceptionsRespond response = new ExceptionsRespond();
        response.setMessage("Task not found. Task either completed or never created");
        response.setTimeDate(LocalDateTime.now());
        response.setErrorCode(101);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(TaskExistException.class)
    public ResponseEntity<Object> TaskExistHandlerException(TaskNotFoundException ex, WebRequest request) {

        ExceptionsRespond response = new ExceptionsRespond();
        response.setMessage("Task with the same description and the same task date already exists");
        response.setTimeDate(LocalDateTime.now());
        response.setErrorCode(101);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);


    }

    @ExceptionHandler(Exception.class)
    public ApiRespond<Object> handleGenericException(Exception ex) {
        return new ApiRespond<>( "An unexpected error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(SearchLengthException.class)
    public ApiRespond<Object> handleGenericException(SearchLengthException ex) {
        return new ApiRespond<>("An unexpected error occurred: " + ex.getMessage());
    }
}




