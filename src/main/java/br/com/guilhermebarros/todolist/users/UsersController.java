package br.com.guilhermebarros.todolist.users;

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
    public ResponseEntity<?> create(@RequestBody UserModel userData) {
        var user = usersRepository.findByUsername(userData.getUsername());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.");
        }

        var createdUser = usersRepository.save(userData);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
