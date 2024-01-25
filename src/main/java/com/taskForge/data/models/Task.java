package com.taskForge.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Data
public class Task {
    @Id
    private String id;
    private LocalDateTime taskDate;
    private String description;
    private LocalDateTime completionDate;
    private String completedTask;

    public String getCompletionDate() {
        if (completionDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'Time' hh:mm a");
            return completionDate.format(formatter);
        }
        return null;


    }
    public String getTaskDate(){
        if (taskDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'Time' hh:mm a");
            return taskDate.format(formatter);
        }
        return null;

    }

}
