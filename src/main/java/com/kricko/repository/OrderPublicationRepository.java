package com.kricko.repository;

import org.springframework.data.repository.CrudRepository;

import com.kricko.domain.OrderPublication;

public interface OrderPublicationRepository extends CrudRepository<OrderPublication, Integer> {
	
}
