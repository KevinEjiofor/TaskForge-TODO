package com.taskForge.services;



import com.taskForge.data.models.Task;
import com.taskForge.data.repositories.CompletedTaskRepository;
import com.taskForge.data.repositories.TaskRepository;
import com.taskForge.dto.Request.CreateTaskRequest;
import com.taskForge.dto.Request.TaskDoneRequest;
import com.taskForge.dto.Request.UpdateTaskRequest;
import com.taskForge.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


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

        when(toDoListRepository.save(any(Task.class))).thenReturn(task);

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

        when(toDoListRepository.findTaskByDescription("Old Task")).thenReturn(Optional.of(existingTask));
        when(toDoListRepository.save(any(Task.class))).thenReturn(existingTask);

        Task result = todoListService.updateTask(updateTaskRequest);

        assertNotNull(result);
        assertEquals("Updated Task", result.getDescription());
    }

    @Test
    public void testFindByDescription() {
        Task task = new Task();
        String description = "Sample Task";

        task.setDescription(description);

        when(toDoListRepository.findTaskByDescription(description)).thenReturn(Optional.of(task));


        Optional<Task> result = toDoListRepository.findTaskByDescription(description);

        assertNotNull(result);
        assertTrue(result.isPresent());
    }



    @Test
    public void testGetAllTasks() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Task> taskPage = new PageImpl<>(Collections.emptyList(), pageRequest, 0);

        when(toDoListRepository.findAll(pageRequest)).thenReturn(taskPage);

        List<Task> result = todoListService.getAllTasks(0, 10);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindAllCompletedTask() {
        List<Task> completedTasks = new ArrayList<>();
        completedTasks.add(new Task());

        when(taskDoneRepository.findAll()).thenReturn(completedTasks);

        List<Task> result = todoListService.findAllCompletedTask();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteByDescription() {
        String description = "TaskToDelete";
        Task task = new Task();
        task.setDescription(description);

        when(toDoListRepository.findTaskByDescription(description)).thenReturn(Optional.of(task));

        todoListService.deleteByDescription(description);

        Mockito.verify(toDoListRepository, Mockito.times(1)).delete(task);
    }
    @Test
    public void testFindByDescriptionAndPage() {
        Task task = new Task();
        String description = "Sample Task";
        task.setDescription(description);

        when(toDoListRepository.findTaskByDescription(description)).thenReturn(Optional.of(task));

        List<Task> result = todoListService.findByDescription(description);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(description, result.get(0).getDescription());

    }
    @Test
    public void testIsTaskComplete_ReturnsTrue() {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setDescription("New Task");
        createTaskRequest.setTaskDate(LocalDateTime.of( 2024,2, 12, 12,10 ));
        createTaskRequest.setCompletionDate(LocalDateTime.of(2024, 2, 12, 12, 50));

//        createTaskRequest.setCompletionDate(LocalDateTime.of(2024,2, 12, 12,50 ));

        Task task = new Task();
        task.setId(String.valueOf(1L));
        task.setDescription("New Task");
        task.setCompletionDate(LocalDateTime.now());

        when(toDoListRepository.save(any(Task.class))).thenReturn(task);

        TaskDoneRequest taskDoneRequest = new TaskDoneRequest();
        taskDoneRequest.setDescription("New Task");

        when(toDoListRepository.findTaskByDescription("New Task")).thenReturn(Optional.of(task));

        boolean result = todoListService.isTaskComplete(taskDoneRequest);

        assertTrue(result);
    }

    @Test
    public void testIsTaskComplete_ReturnsFalse() {
        String description = "Test Task";
        LocalDateTime taskDate = LocalDateTime.now().plusDays(1);
        LocalDateTime completionDate = LocalDateTime.now();
        Task task = new Task();
        task.setDescription(description);
        task.setTaskDate(taskDate);

        TaskDoneRequest taskDoneRequest = new TaskDoneRequest();
        taskDoneRequest.setDescription(description);
        taskDoneRequest.setCompletionDate(completionDate);

        when(toDoListRepository.findTaskByDescription(description)).thenReturn(Optional.of(task));

        boolean result = todoListService.isTaskComplete(taskDoneRequest);

        assertFalse(result);
    }

    @Test
    public void testIsTaskComplete_TaskNotFound() {
        String description = "Non-existent Task";
        LocalDateTime completionDate = LocalDateTime.now();

        TaskDoneRequest taskDoneRequest = new TaskDoneRequest();
        taskDoneRequest.setDescription(description);
        taskDoneRequest.setCompletionDate(completionDate);

        when(toDoListRepository.findTaskByDescription(description)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            todoListService.isTaskComplete(taskDoneRequest);
        });
    }
}

