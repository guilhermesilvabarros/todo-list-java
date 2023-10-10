package br.com.guilhermebarros.todolist.users;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
    @PostMapping
    public void create(@RequestBody UserModel user) {
        System.out.println(user.getName());
    }
}