package com.taskForge.services;


import com.taskForge.data.models.Task;
import com.taskForge.data.repositories.CompletedTaskRepository;
import com.taskForge.data.repositories.TaskRepository;
import com.taskForge.dto.Request.CreateTaskRequest;
import com.taskForge.dto.Request.TaskDoneRequest;
import com.taskForge.dto.Request.UpdateTaskRequest;
import com.taskForge.exceptions.SearchLengthException;
import com.taskForge.exceptions.TaskExistException;
import com.taskForge.exceptions.TaskNotCompletedException;
import com.taskForge.exceptions.TaskNotFoundException;
import com.taskForge.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static com.taskForge.utils.Mapper.*;


@Service
@Slf4j
public class TodoListServiceImpl implements TodoListService {
    @Autowired
    private TaskRepository toDoListRepository;

    @Autowired
    private CompletedTaskRepository taskDoneRepository;
    @Override
    public Task createNewTask(CreateTaskRequest createTaskRequest) {
        validate(createTaskRequest.getDescription(),createTaskRequest.getTaskDate());
        return toDoListRepository.save(createTaskRequestMap(createTaskRequest));

    }

    @Override
    public Task updateTask(UpdateTaskRequest updateTaskRequest) {
        Task existingTask = findTask(updateTaskRequest.getOldDescription());
        return toDoListRepository.save(updateTaskRequestMap(updateTaskRequest, existingTask));
    }

    @Override
    public List<Task> findByDescription(String description) {
        if (description.length() < 3) {
            throw new SearchLengthException("Description must be at least 3 characters for search.");
        }

        Optional<Task> exactMatch = toDoListRepository.findTaskByDescription(description);
        if(exactMatch.isEmpty()){
            throw new TaskNotFoundException("Task Not Found");
        }

        return Collections.singletonList(exactMatch.get());
    }




    @Override
    public boolean isTaskComplete(TaskDoneRequest taskRequest) {

            Task task = findTask(taskRequest.getDescription());
            LocalDateTime completionDate = Mapper.parseDate(task.getCompletionDate());
         if(completionDate.isBefore(LocalDateTime.now() )|| completionDate.equals(LocalDateTime.now())){


             taskRequest.setCompletedTask(true);
             toDoListRepository.save(taskCompleted(taskRequest));
         }else {
             throw new TaskNotCompletedException("You are meant to finish the task " + taskRequest.getCompletionDate());

         }


         return taskRequest.isCompletedTask();
    }

    @Override
    public List<Task> getAllTasks(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Task> taskPage = toDoListRepository.findAll(pageRequest);
        return taskPage.getContent();

    }

    @Override
    public List<Task> findAllCompletedTask() {
        List<Task> allTask = taskDoneRepository.findAll();
        if (allTask.isEmpty()){
            throw new TaskNotFoundException("Task not found. Task either never created or not completed");
        }
        return allTask;


    }

    @Override
    public void deleteByDescription(String description) {
        Task task = findTask(description);
        if (task.getDescription().equalsIgnoreCase(description))
            toDoListRepository.delete(task);

    }
    public void validate(String description, LocalDateTime taskDate) {
        Optional<Task> existingTask = toDoListRepository.findTaskByDescription(description);

        existingTask.ifPresent(task -> {
//            LocalDateTime existingTaskDate = LocalDateTime.parse(task.getTaskDate(), DateTimeFormatter.ofPattern("MMMM dd, yyyy 'Time' hh:mm a"));
            LocalDateTime existingTaskDate = Mapper.parseDate(task.getTaskDate());
            if (existingTaskDate.equals(taskDate)) {
                throw new TaskExistException("Task with the same description and the same task date already exists");
            }
        });
    }

    private Task findTask(String description){
        Optional<Task> task = toDoListRepository.findTaskByDescription(description);
        if (task.isPresent()){
            return task.get();
        }
        throw new TaskNotFoundException("Task not found. Task either completed or never created");

    }
}
