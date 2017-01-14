package com.kricko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.repository.UserRepository;


@RestController
public class UserController {

	@Autowired
	UserRepository userRepo;
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (User)auth.getPrincipal();
    }
	
	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody com.kricko.domain.User user) {
		userRepo.save(user);
    }
}
