package com.taskForge.expections;

import com.taskForge.dto.Respond.ApiRespond;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiRespond<Object> handleTaskNotFoundException(TaskNotFoundException ex) {
        return new ApiRespond<>(null, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiRespond<Object> handleGenericException(Exception ex) {
        return new ApiRespond<>(null, "An unexpected error occurred: " + ex.getMessage());
    }
}
