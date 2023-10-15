package br.com.guilhermebarros.todolist.controllers;

import br.com.guilhermebarros.todolist.entities.Task;
import br.com.guilhermebarros.todolist.repositories.ITasksRepository;
import br.com.guilhermebarros.todolist.utils.CopyNonNullProperties;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    @Autowired
    private ITasksRepository tasksRepository;

    @GetMapping
    public List<Task> fetch(HttpServletRequest request) {
        var userIdString = request.getAttribute("userId").toString();
        var userId = UUID.fromString(userIdString);

        return tasksRepository.findManyByUserId(userId);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> update(@RequestBody Task taskProps, @PathVariable UUID taskId,
                                    HttpServletRequest request) {

        var task = tasksRepository.findById(taskId).orElse(null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }

        var userIdString = request.getAttribute("userId").toString();
        var userId = UUID.fromString(userIdString);

        if (!task.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body("Not allowed to update a task from another user.");
        }

        CopyNonNullProperties.execute(taskProps, task);

        tasksRepository.save(taskProps);

        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Task taskProps, HttpServletRequest request) {
        var userIdString = request.getAttribute("userId").toString();

        var userId = UUID.fromString(userIdString);
        taskProps.setUserId(userId);

        var currentDate = LocalDateTime.now();

        if (taskProps.getStartAt().isBefore(currentDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Start date must be after the current date.");
        }

        if (taskProps.getEndAt().isBefore(taskProps.getStartAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("End date must be after the start date.");
        }

        var createdTask = tasksRepository.save(taskProps);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
}
