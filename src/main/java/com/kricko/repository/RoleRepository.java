/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.repository;

import com.kricko.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
