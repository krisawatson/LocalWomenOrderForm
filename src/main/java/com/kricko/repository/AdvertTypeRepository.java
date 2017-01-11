package com.kricko.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kricko.domain.AdvertType;

public interface AdvertTypeRepository extends CrudRepository<AdvertType, Integer>{
	
	List<AdvertType> findAll();
}
