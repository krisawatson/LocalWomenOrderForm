package com.kricko.repository;

import org.springframework.data.repository.CrudRepository;

import com.kricko.domain.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    public Account findByUsername(String username);
}
