package com.taskForge.services;



import com.taskForge.data.models.Task;
import com.taskForge.data.repositories.CompletedTaskRepository;
import com.taskForge.data.repositories.TaskRepository;
import com.taskForge.dto.Request.CreateTaskRequest;
import com.taskForge.dto.Request.UpdateTaskRequest;
import com.taskForge.expections.TaskNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
class TodoListServiceImplTest {
    @Mock
    private TaskRepository toDoListRepository;

    @Mock
    private CompletedTaskRepository taskDoneRepository;

    @InjectMocks
    private TodoListServiceImpl todoListService;

    @Test
    public void testCreateNewTask() {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setDescription("New Task");

        Task task = new Task();
        task.setId(String.valueOf(1L));
        task.setDescription("New Task");

        Mockito.when(toDoListRepository.save(any(Task.class))).thenReturn(task);

        Task result = todoListService.createNewTask(createTaskRequest);

        assertNotNull(result);
        assertEquals("New Task", result.getDescription());
    }

    @Test
    public void testUpdateTask() {
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setOldDescription("Old Task");
        updateTaskRequest.setNewDescription("Updated Task");

        Task existingTask = new Task();
        existingTask.setDescription("Old Task");

        Mockito.when(toDoListRepository.findTaskByDescription("Old Task")).thenReturn(Optional.of(existingTask));
        Mockito.when(toDoListRepository.save(any(Task.class))).thenReturn(existingTask);

        Task result = todoListService.updateTask(updateTaskRequest);

        assertNotNull(result);
        assertEquals("Updated Task", result.getDescription());
    }

    @Test
    public void testFindByDescription() {
        String description = "Sample Task";
        Task task = new Task();
        task.setDescription(description);

        Mockito.when(toDoListRepository.findTaskByDescription(description)).thenReturn(Optional.of(task));

        Task result = todoListService.findByDescription(description);

        assertNotNull(result);
        assertEquals(description, result.getDescription());
    }

    @Test
    public void testIsTaskComplete() {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        assertFalse(todoListService.isTaskComplete(createTaskRequest));
    }

    @Test
    public void testGetAllTasks() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Task> taskPage = new PageImpl<>(Collections.emptyList(), pageRequest, 0);

        Mockito.when(toDoListRepository.findAll(pageRequest)).thenReturn(taskPage);

        List<Task> result = todoListService.getAllTasks(0, 10);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindAllCompletedTask() {
        List<Task> completedTasks = new ArrayList<>();
        completedTasks.add(new Task());

        Mockito.when(taskDoneRepository.findAll()).thenReturn(completedTasks);

        List<Task> result = todoListService.findAllCompletedTask();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteByDescription() {
        String description = "TaskToDelete";
        Task task = new Task();
        task.setDescription(description);

        Mockito.when(toDoListRepository.findTaskByDescription(description)).thenReturn(Optional.of(task));

        todoListService.deleteByDescription(description);

        Mockito.verify(toDoListRepository, Mockito.times(1)).delete(task);
    }

    @Test
    public void testValidate() {
        String description = "ExistingTask";
        Mockito.when(toDoListRepository.findTaskByDescription(description)).thenReturn(Optional.of(new Task()));

        assertThrows(TaskNotFoundException.class, () -> todoListService.validate(description));
    }
}

