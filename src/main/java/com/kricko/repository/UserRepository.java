package com.kricko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kricko.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
    
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.firstname = :firstname, u.lastname = :lastname, u.email = :email, u.roleId = :roleId, u.enabled = :enabled WHERE u.id = :id")
    void updateUserInfoById(@Param("id") Long id, @Param("firstname") String firstname, @Param("lastname") String lastname, 
                         @Param("email") String email, @Param("roleId") Long roleId, @Param("enabled") boolean enabled);
}
