package com.taskForge.exceptions;

public class TaskNotCompletedException extends TaskNotFoundException{
    public TaskNotCompletedException(String message) {
        super(message);
    }
}
