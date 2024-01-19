package com.taskForge.services;


import com.taskForge.data.models.Task;
import com.taskForge.data.repositories.CompletedTaskRepository;
import com.taskForge.data.repositories.TaskRepository;
import com.taskForge.dto.Request.CreateTaskRequest;
import com.taskForge.dto.Request.UpdateTaskRequest;
import com.taskForge.expections.TaskNotFoundException;
import com.taskForge.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.taskForge.utils.Mapper.map;


@Service
public class TodoListServiceImpl implements TodoListService {
    @Autowired
    private TaskRepository toDoListRepository;

    @Autowired
    private CompletedTaskRepository taskDoneRepository;
    @Override
    public Task createNewTask(CreateTaskRequest createTaskRequest) {
        validate(createTaskRequest.getDescription());
        return toDoListRepository.save(map(createTaskRequest));

    }

    @Override
    public Task updateTask(UpdateTaskRequest updateTaskRequest) {
        Task existingTask = findByDescription(updateTaskRequest.getOldDescription());
        return toDoListRepository.save(map(updateTaskRequest, existingTask));
    }

    @Override
    public Task findByDescription(String description) {
        Optional<Task> task = toDoListRepository.findTaskByDescription(description);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task not found. Task either completed or never created");
        }

        return task.get();
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
        Task task = findByDescription(description);
        if (task.getDescription().equalsIgnoreCase(description))
            toDoListRepository.delete(task);

    }
    public void validate(String description) {
        if (toDoListRepository.findTaskByDescription(description).isPresent()){
            throw new TaskNotFoundException("Task description exist already");

        }
    }
}
