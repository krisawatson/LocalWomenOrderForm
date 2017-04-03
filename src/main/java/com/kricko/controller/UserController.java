package com.kricko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.domain.Role;
import com.kricko.domain.User;
import com.kricko.service.UserService;


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
    public void updateUser(@PathVariable(value="id") Long userId, @RequestBody User user) {
	    userService.updateUser(userId, user);
    }
}
