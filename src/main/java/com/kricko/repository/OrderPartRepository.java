package com.kricko.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kricko.domain.OrderPart;

public interface OrderPartRepository extends JpaRepository<OrderPart, Long> {
	
}
