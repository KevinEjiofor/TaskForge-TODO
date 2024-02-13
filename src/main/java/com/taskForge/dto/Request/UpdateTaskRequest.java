package com.taskForge.dto.Request;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class UpdateTaskRequest {
    @Id
    private String id;
    private LocalDateTime taskDate;
    private String oldDescription;
    private LocalDateTime oldTaskDate;
    private LocalDateTime oldCompletionDate;
    private String newDescription;
    private Boolean completedTask;
    private LocalDateTime completionDate;
}
