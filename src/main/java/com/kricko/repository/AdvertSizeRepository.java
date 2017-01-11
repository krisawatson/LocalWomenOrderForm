package com.kricko.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kricko.domain.AdvertSize;

public interface AdvertSizeRepository extends CrudRepository<AdvertSize, Integer>{
	
	List<AdvertSize> findAll();
}
