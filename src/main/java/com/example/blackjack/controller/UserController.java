package com.example.blackjack.controller;

import com.example.blackjack.service.UserService;
import com.example.blackjack.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dima on 28.07.17.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users")
    private List<User> findAllTUsers() {
        return userService.findAllUsers();
    }

    @RequestMapping(value = "/users/{id}")
    private User findUser(@PathVariable("id") long id) {
        return userService.findUser(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    private void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    private void updateUser(@PathVariable long id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
    private void removeUser(@PathVariable long id) {
        userService.removeUser(id);
    }
}
