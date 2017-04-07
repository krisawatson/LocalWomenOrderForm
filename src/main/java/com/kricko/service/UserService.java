package com.kricko.service;

import com.kricko.domain.Role;
import com.kricko.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    List<Role> getRoles();

    ResponseEntity<Void> createUser(@RequestBody User user);

    @Transactional
    void updateUser(Long userId, User user);

    User getUserByUsername(String username);
}
