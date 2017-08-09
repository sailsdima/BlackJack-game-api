package com.example.blackjack.controller;

import com.example.blackjack.entity.User;
import com.example.blackjack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dima on 28.07.17.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "")
    private List<User> findAllTUsers() {
        return userService.findAllUsers();
    }

    @RequestMapping(value = "/{id}")
    private User findUser(@PathVariable("id") long id) {
        return userService.findUser(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    private User addUser(@RequestBody User user) {
        user.setBalance(new BigDecimal("0"));
        user.setDrawBalance(new BigDecimal("0"));
        return userService.addUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    private User updateUserName(@PathVariable long id, @RequestBody User user) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    private Long removeUser(@PathVariable long id) {
        return userService.removeUser(id);
    }
}
