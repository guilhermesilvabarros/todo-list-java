package br.com.guilhermebarros.todolist.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.guilhermebarros.todolist.repositories.IUsersRepository;
import br.com.guilhermebarros.todolist.entities.User;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private IUsersRepository usersRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @NotNull User userProps) {
        var user = usersRepository.findByUsername(userProps.getUsername());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.");
        }

        var passwordHash = BCrypt.withDefaults()
                .hashToString(12, userProps.getPassword().toCharArray());

        userProps.setPassword(passwordHash);
        var createdUser = usersRepository.save(userProps);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
