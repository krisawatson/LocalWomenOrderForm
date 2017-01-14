package com.kricko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kricko.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
    
    @Modifying
    @Query("update User u set u.firstname = ?2, u.lastname = ?3, u.email = ?4, u.role_id = ?5, u.enabled = ?6 where u.id = ?1")
    void setUserInfoById(Long id, String firstname, String lastname, String email, Long roleId, boolean enabled);
}
