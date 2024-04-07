package com.taskForge.Controller;


import com.taskForge.data.models.Task;
import com.taskForge.dto.Request.CreateTaskRequest;
import com.taskForge.dto.Request.UpdateTaskRequest;
import com.taskForge.dto.Respond.ApiRespond;
import com.taskForge.services.TodoListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class TaskController {
    @Autowired
    private TodoListService toDoListService;



    @PostMapping("/createTasks")
    public ApiRespond<Object> createNewTask(@RequestBody CreateTaskRequest createTaskRequest) {

            Task createdTask = toDoListService.createNewTask(createTaskRequest);
            return new ApiRespond<>(createdTask);




    }


    @GetMapping("/findByDescription")
    public ApiRespond<Object> findByDescription(@RequestParam String description) {

        List<Task> task = toDoListService.findByDescription(description);
        return new ApiRespond<>(task);

    }


    @DeleteMapping("/deleteByDescription")
    public ApiRespond<Object> deleteTaskByDescription(String description){

                toDoListService.deleteByDescription(description);
                String task = "Task has been deleted successfully";
                return new ApiRespond<>(task);


    }

    @PostMapping("/update")
    public ApiRespond<Object> updateTask(@RequestBody UpdateTaskRequest updateTaskRequest){

            Task updateTask = toDoListService.updateTask(updateTaskRequest);
            return new ApiRespond<>(updateTask);


    }


    @GetMapping("/tasks")
    public ApiRespond<Object> getAllTask(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int pageSize) {

            List<Task> tasks = toDoListService.getAllTasks(page, pageSize);
            return new ApiRespond<>(tasks);


    }


}
