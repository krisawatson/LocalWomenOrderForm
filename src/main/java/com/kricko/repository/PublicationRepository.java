package com.kricko.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kricko.domain.Publication;

public interface PublicationRepository extends JpaRepository<Publication, Integer>{
}
