package br.com.guilhermebarros.todolist.controllers;

import br.com.guilhermebarros.todolist.entities.Task;
import br.com.guilhermebarros.todolist.repositories.ITasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    @Autowired
    private ITasksRepository tasksRepository;

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task taskProps) {
        System.out.println("Chegou no controller.");

        var createdTask = tasksRepository.save(taskProps);

        return ResponseEntity.status(201).body(createdTask);
    }
}
