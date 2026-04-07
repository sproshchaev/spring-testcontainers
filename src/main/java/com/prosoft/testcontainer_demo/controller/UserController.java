package com.prosoft.testcontainer_demo.controller;

import com.prosoft.testcontainer_demo.entity.User;
import com.prosoft.testcontainer_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String sayHello() {
        return "Hello, World";
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    // todo дописать остальные эндпоинты

}
