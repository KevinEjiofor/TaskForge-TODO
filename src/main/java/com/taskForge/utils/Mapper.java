package com.taskForge.utils;


import com.taskForge.data.models.Task;
import com.taskForge.dto.Request.CreateTaskRequest;
import com.taskForge.dto.Request.TaskDoneRequest;
import com.taskForge.dto.Request.UpdateTaskRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mapper {
    public static Task createTaskRequestMap(CreateTaskRequest createTaskRequest){
        Task task = new Task();


        task.setId(createTaskRequest.getId());
        task.setDescription(createTaskRequest.getDescription());
        task.setTaskDate(createTaskRequest.getTaskDate());
        task.setCompletionDate(createTaskRequest.getCompletionDate());
        task.setCompletedTask(createTaskRequest.isCompletedTask());

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
    public  static Task taskCompleted(TaskDoneRequest taskDoneRequest){
        Task completedTask = new Task();

        completedTask.setDescription(taskDoneRequest.getDescription());
        completedTask.setCompletedTask(taskDoneRequest.isCompletedTask());

        return completedTask;



    }
    public static LocalDateTime parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'Time' hh:mm a");
        return LocalDateTime.parse(dateString, formatter);
    }

}
