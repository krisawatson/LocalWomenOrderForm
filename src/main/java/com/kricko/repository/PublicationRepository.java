package com.kricko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kricko.domain.Publication;

public interface PublicationRepository extends JpaRepository<Publication, Long>{

	@Modifying(clearAutomatically = true)
    @Query("UPDATE Publication p SET p.name = :name, p.email = :email, p.enabled = :enabled WHERE p.id = :id")
    void updatePublicationInfoById(@Param("id") Long id, @Param("name") String name, 
                @Param("email") String email, @Param("enabled") boolean enabled);
}
