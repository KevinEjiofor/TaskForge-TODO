package com.taskForge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.taskForge.Controller", "com.taskForge.exceptions", "com.taskForge"})
public class TaskForgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskForgeApplication.class, args);
	}

}
