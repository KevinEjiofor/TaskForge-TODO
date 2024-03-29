package com.taskForge.services;




import com.taskForge.data.models.Task;
import com.taskForge.dto.Request.CreateTaskRequest;
import com.taskForge.dto.Request.TaskDoneRequest;
import com.taskForge.dto.Request.UpdateTaskRequest;

import java.util.List;

public interface TodoListService {

    Task createNewTask(CreateTaskRequest createTaskRequest);
    Task updateTask(UpdateTaskRequest updateTaskRequest);
    List<Task> findByDescription(String description);
    boolean isTaskComplete(TaskDoneRequest taskDoneRequest);
    List<Task> getAllTasks(int page, int pageSize);
    List<Task> findAllCompletedTask();

    void deleteByDescription(String description);



}
