package com.kricko.repository;

import org.springframework.data.repository.CrudRepository;

import com.kricko.domain.OrderPart;

public interface OrderPartRepository extends CrudRepository<OrderPart, Integer> {
	
}
