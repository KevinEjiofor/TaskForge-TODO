package com.taskForge.dto.Request;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TaskDoneRequest {
    private String id;
    private LocalDateTime taskDate;
    private String description;
    private LocalDateTime completionDate;
    private boolean completedTask;
}
