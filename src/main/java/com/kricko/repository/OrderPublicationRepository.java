package com.kricko.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kricko.domain.OrderPublication;

public interface OrderPublicationRepository extends JpaRepository<OrderPublication, Long> {
	
}
