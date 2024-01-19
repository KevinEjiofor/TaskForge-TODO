package com.taskForge.expections;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException (String message){
        super(message);
    }
}
