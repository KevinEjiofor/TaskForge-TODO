package com.taskForge.Controller;


import com.taskForge.data.models.Task;
import com.taskForge.dto.Request.CreateTaskRequest;
import com.taskForge.dto.Request.UpdateTaskRequest;
import com.taskForge.dto.Respond.ApiRespond;
import com.taskForge.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:63342")

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class TaskController {
    @Autowired
    private TodoListService toDoListService;



    @PostMapping("/createTasks")
    public ApiRespond<Object> createNewTask(@RequestBody CreateTaskRequest createTaskRequest) {
        try {
            Task createdTask = toDoListService.createNewTask(createTaskRequest);
            return new ApiRespond<>(createdTask);
        } catch (Exception e) {
            return new ApiRespond<>(e.getMessage());
        }
    }
    @GetMapping("/findByDescription")
    public ApiRespond<Object> findByDescription(String description){
        try {
            Task task = toDoListService.findByDescription(description);
            return new ApiRespond<>(task);

        }catch (Exception e) {
            return new ApiRespond<>(e.getMessage());

        }
    }
    @DeleteMapping("/deleteByDescription")
    public ApiRespond<String> deleteTaskByDescription(String description){
        try{
            toDoListService.deleteByDescription(description);
            String task = "Task has been deleted successfully";
            return new ApiRespond<>(task);

        }catch (Exception e){
            return new ApiRespond<>(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ApiRespond<Object> updateTask(@RequestBody UpdateTaskRequest updateTaskRequest){
        try {
            Task updateTask = toDoListService.updateTask(updateTaskRequest);
            return new ApiRespond<>(updateTask);
        } catch (Exception e) {
            return new ApiRespond<>(e.getMessage());
        }
    }


    @GetMapping("/tasks")
    public ApiRespond<Object> getAllTask(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<Task> tasks = toDoListService.getAllTasks(page, pageSize);
            return new ApiRespond<>(tasks);
        } catch (Exception e) {
            return new ApiRespond<>(e.getMessage());
        }
    }


}
