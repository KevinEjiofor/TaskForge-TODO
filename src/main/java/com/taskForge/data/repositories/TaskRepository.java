package com.taskForge.data.repositories;


import com.taskForge.data.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task,String> {
    Optional<Task> findTaskByDescription(String description);

}
