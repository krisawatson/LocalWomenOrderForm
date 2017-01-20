package com.kricko.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.kricko.domain.Role;
import com.kricko.domain.User;

public interface UserService
{

    public List<User> getUsers();
    
    public List<Role> getRoles();
    
    public ResponseEntity<Void> createUser(@RequestBody User user);
    
    @Transactional
    public ResponseEntity<Void> updateUser(Long userId, User user);
    
    public User getUserByUsername(String username);
}
