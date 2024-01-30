package com.taskForge.utils;


import com.taskForge.data.models.Task;
import com.taskForge.dto.Request.CreateTaskRequest;
import com.taskForge.dto.Request.UpdateTaskRequest;

public class Mapper {
    public static Task createTaskRequestMap(CreateTaskRequest createTaskRequest){
        Task task = new Task();


        task.setId(createTaskRequest.getId());
        task.setDescription(createTaskRequest.getDescription());
        task.setTaskDate(createTaskRequest.getTaskDate());
        task.setCompletionDate(createTaskRequest.getCompletionDate());
        task.setCompletedTask(createTaskRequest.getCompletedTask());

        return task;
    }
    public static Task updateTaskRequestMap(UpdateTaskRequest updateTaskRequest, Task existingTask) {
        existingTask.setDescription(updateTaskRequest.getNewDescription());

        if (updateTaskRequest.getCompletedTask() == null || updateTaskRequest.getTaskDate() == null) {
            existingTask.setTaskDate(updateTaskRequest.getOldTaskDate());
            existingTask.setCompletionDate(updateTaskRequest.getOldCompletionDate());
            existingTask.setCompletedTask(updateTaskRequest.getCompletedTask());

        }else {
            existingTask.setTaskDate(updateTaskRequest.getTaskDate());
            existingTask.setCompletionDate(updateTaskRequest.getCompletionDate());
            existingTask.setCompletedTask(updateTaskRequest.getCompletedTask());
        }


        return existingTask;
    }

}
