package com.kricko.repository;

import org.springframework.data.repository.CrudRepository;

import com.kricko.domain.Business;

public interface BusinessRepository extends CrudRepository<Business, Integer> {
	
}
