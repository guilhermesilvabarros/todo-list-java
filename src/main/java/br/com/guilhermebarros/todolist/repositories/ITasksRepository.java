package br.com.guilhermebarros.todolist.repositories;

import br.com.guilhermebarros.todolist.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITasksRepository extends JpaRepository<Task, UUID> { }
