package com.kricko.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kricko.domain.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
	
}
