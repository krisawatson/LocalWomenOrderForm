/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.service;

import com.kricko.domain.Role;
import com.kricko.domain.User;
import com.kricko.repository.RoleRepository;
import com.kricko.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();

    private final
    PasswordEncoder passwordEncoder;

    private final
    UserRepository userRepo;

    private final
    RoleRepository roleRepo;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepo, RoleRepository roleRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    @Override
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepo.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            if (e.contains(ConstraintViolationException.class)) {
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

    @Override
    public void updateUser(Long userId, User user) {
        User dbUser = userRepo.getOne(userId);
        dbUser.setEmail(user.getEmail());
        dbUser.setEnabled(user.getEnabled());
        dbUser.setFirstname(user.getFirstname());
        dbUser.setLastname(user.getLastname());
        dbUser.setRoleId(user.getRoleId());
        dbUser.setUsername(user.getUsername());
        if (null != user.getPassword()) {
            dbUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepo.saveAndFlush(dbUser);
        new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
