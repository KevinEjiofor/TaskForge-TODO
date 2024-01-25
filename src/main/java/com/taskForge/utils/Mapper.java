package com.taskForge.utils;


import com.taskForge.data.models.Task;
import com.taskForge.dto.Request.CreateTaskRequest;
import com.taskForge.dto.Request.UpdateTaskRequest;

public class Mapper {
    public static Task map(CreateTaskRequest createTaskRequest){
        Task task = new Task();


        task.setId(createTaskRequest.getId());
        task.setDescription(createTaskRequest.getDescription());
        task.setTaskDate(createTaskRequest.getTaskDate());
        task.setCompletionDate(createTaskRequest.getCompletionDate());
        task.setCompletedTask(createTaskRequest.getCompletedTask());

        return task;
    }
    public static Task map(UpdateTaskRequest updateTaskRequest, Task existingTask) {

        existingTask.setDescription(updateTaskRequest.getNewDescription());
        existingTask.setTaskDate(updateTaskRequest.getTaskDate());
        existingTask.setCompletionDate(updateTaskRequest.getCompletionDate());
        existingTask.setCompletedTask(updateTaskRequest.getCompletedTask());

        return existingTask;
    }

}
