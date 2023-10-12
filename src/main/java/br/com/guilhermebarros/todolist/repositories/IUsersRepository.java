package br.com.guilhermebarros.todolist.repositories;

import br.com.guilhermebarros.todolist.entities.User;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
}
