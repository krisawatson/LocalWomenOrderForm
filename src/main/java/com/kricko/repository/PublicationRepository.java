package com.kricko.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kricko.domain.Publication;

public interface PublicationRepository extends CrudRepository<Publication, Integer>{
	
	List<Publication> findAll();
}
