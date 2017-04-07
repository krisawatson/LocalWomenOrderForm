/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.repository;

import com.kricko.domain.OrderPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPartRepository extends JpaRepository<OrderPart, Long> {

}
