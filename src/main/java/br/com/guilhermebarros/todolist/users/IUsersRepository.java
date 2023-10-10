package br.com.guilhermebarros.todolist.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);
}
