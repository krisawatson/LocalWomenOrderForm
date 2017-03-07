package com.kricko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.repository.UserRepository;

@RestController
public class AuthController {

	@Autowired
    UserRepository userRepo;
	
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
    public User getAuthUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (User)auth.getPrincipal();
    }
	
	@RequestMapping(value = "/auth/id", method = RequestMethod.GET)
    public Long getAuthUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userRepo.findByUsername(auth.getName()).getId();
    }
}
