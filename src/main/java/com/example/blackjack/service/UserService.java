package com.example.blackjack.service;

import com.example.blackjack.entity.User;
import com.example.blackjack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 28.07.17.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User findUser(long id) {
        return userRepository.findOne(id);
    }

    public User addUser(User user) {
        userRepository.save(user);
        return user;
    }

    public User updateUser(User user) {
        User oldUser = findUser(user.getId());
        oldUser.setName(user.getName());
        userRepository.save(oldUser);
        return oldUser;
    }

    public Long removeUser(long id) {
        return userRepository.deleteById(id);
    }

}
