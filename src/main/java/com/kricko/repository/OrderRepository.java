package com.kricko.repository;

import org.springframework.data.repository.CrudRepository;

import com.kricko.domain.Orders;

public interface OrderRepository extends CrudRepository<Orders, Integer> {
	
}
