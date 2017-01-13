package com.kricko.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kricko.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
