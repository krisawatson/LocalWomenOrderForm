package com.kricko.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@RequestMapping(value = "/auth", method = RequestMethod.GET)
    public User getAuthUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (User)auth.getPrincipal();
    }
}
