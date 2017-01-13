package com.kricko.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kricko.domain.Business;

public interface BusinessRepository extends JpaRepository<Business, Long> {
	
}
