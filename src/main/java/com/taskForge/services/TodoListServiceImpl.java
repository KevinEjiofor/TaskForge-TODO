package com.taskForge.services;


import com.taskForge.data.models.Task;
import com.taskForge.data.repositories.CompletedTaskRepository;
import com.taskForge.data.repositories.TaskRepository;
import com.taskForge.dto.Request.CreateTaskRequest;
import com.taskForge.dto.Request.UpdateTaskRequest;
import com.taskForge.expections.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.taskForge.utils.Mapper.*;


@Service
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
        Optional<Task> exactMatch = toDoListRepository.findTaskByDescription(description);

        if (exactMatch.isPresent()) {
            return Collections.singletonList(exactMatch.get());
        } else if (description.length() < 3) {
            throw new IllegalArgumentException("Description length must be at least 3 characters for search.");
        } else {
            return toDoListRepository.findTasksByDescriptionStartingWith(description.substring(0, 3));
        }
    }




    @Override
    public boolean isTaskComplete(CreateTaskRequest createTaskRequest) {
        return false;
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

        if (existingTask.isPresent()){
            if(existingTask.get().getTaskDate().equalsIgnoreCase(String.valueOf(taskDate)))
                throw new TaskNotFoundException("Task with the same description and the same task date already exists");
        }
    }


//        public void validate(String description) {
//        if (toDoListRepository.findTaskByDescription(description).isPresent()){
//            throw new TaskNotFoundException("Task description exist already");
//
//        }
//    }
    private Task findTask(String description){
        Optional<Task> task = toDoListRepository.findTaskByDescription(description);
        if (task.isPresent()){
            return task.get();
        }
        throw new TaskNotFoundException("Task not found. Task either completed or never created");

    }
}
