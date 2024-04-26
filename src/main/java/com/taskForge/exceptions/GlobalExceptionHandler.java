package com.taskForge.exceptions;

import com.taskForge.dto.Request.TaskDoneRequest;
import com.taskForge.dto.Respond.ExceptionsRespond;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Object> handleExceptions(TaskNotFoundException ex) {
        ExceptionsRespond response = new ExceptionsRespond();
        response.setMessage(ex.getMessage());
        response.setTimeDate(LocalDateTime.now());
        response.setErrorCode(101);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(TaskExistException.class)
    public ResponseEntity<Object> TaskExistHandlerException(TaskNotFoundException ex) {

        ExceptionsRespond response = new ExceptionsRespond();
        response.setMessage(ex.getMessage());
        response.setTimeDate(LocalDateTime.now());
        response.setErrorCode(101);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);


    }
    @ExceptionHandler(TaskNotCompletedException.class)
    public ResponseEntity<Object> completeTaskHandlerException(TaskNotFoundException ex) {

        ExceptionsRespond response = new ExceptionsRespond();
        TaskDoneRequest taskRequest = new TaskDoneRequest();
        response.setMessage( ex.getMessage() + "You are meant to finish the task " + taskRequest.getCompletionDate());
        response.setTimeDate(LocalDateTime.now());
        response.setErrorCode(102);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);


    }
//
//    @ExceptionHandler(Exception.class)
//    public ApiRespond<Object> handleGenericException(Exception ex) {
//        return new ApiRespond<>( "An unexpected error occurred: " + ex.getMessage());
//    }

    @ExceptionHandler(SearchLengthException.class)
    public ResponseEntity<Object> handleGenericException(SearchLengthException ex) {

        ExceptionsRespond response = new ExceptionsRespond();
        response.setMessage(ex.getMessage());
        response.setTimeDate(LocalDateTime.now());
        response.setErrorCode(104);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}