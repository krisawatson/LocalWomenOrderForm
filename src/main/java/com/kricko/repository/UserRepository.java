package com.kricko.repository;

import org.springframework.data.repository.CrudRepository;

import com.kricko.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByUsername(String username);
}
