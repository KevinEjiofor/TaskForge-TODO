package com.taskForge.data.repositories;


import com.taskForge.data.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompletedTaskRepository extends MongoRepository<Task, String> {
}
