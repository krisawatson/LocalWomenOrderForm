package com.kricko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kricko.domain.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserId (Long id);
}
