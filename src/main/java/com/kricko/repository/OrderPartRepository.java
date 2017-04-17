/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.repository;

import com.kricko.domain.OrderPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderPartRepository extends JpaRepository<OrderPart, Long> {
    List<OrderPart> findByOrdersId(Long id);
}
