package com.taskForge.exceptions;

public class SearchLengthException extends TaskNotFoundException{
    public SearchLengthException(String message) {
        super(message);
    }
}
