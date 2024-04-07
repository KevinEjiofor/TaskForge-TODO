package com.taskForge.exceptions;


public class TaskExistException extends TaskNotFoundException {
    public TaskExistException(String message) {
        super(message);
    }
}
