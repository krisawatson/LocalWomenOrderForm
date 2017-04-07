/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.controller;

import com.kricko.domain.Role;
import com.kricko.domain.User;
import com.kricko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List<Role> getRoles() {
        return userService.getRoles();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
    @Transactional
    public void updateUser(@PathVariable(value = "id") Long userId, @RequestBody User user) {
        userService.updateUser(userId, user);
    }
}
