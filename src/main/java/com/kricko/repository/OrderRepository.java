/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.repository;

import com.kricko.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
