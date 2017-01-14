package com.kricko.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.domain.Role;
import com.kricko.domain.User;
import com.kricko.repository.RoleRepository;
import com.kricko.repository.UserRepository;


@RestController
public class UserController {

	private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public List<User> getUsers() {
		return userRepo.findAll();
    }
	
	@RequestMapping(value = "/user/roles", method = RequestMethod.GET)
    public List<Role> getRoles() {
		return roleRepo.findAll();
    }
	
	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody com.kricko.domain.User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		try{
			userRepo.save(user);
		} catch (DataIntegrityViolationException e) {
			if(e.contains(ConstraintViolationException.class)) {
				LOGGER.warn("Constaint violated while trying to create an account, "
						+ "attempting to create account with same username", e);
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			LOGGER.error("Exception while trying to create an account", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
