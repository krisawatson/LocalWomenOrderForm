/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.repository;

import com.kricko.domain.OrderPublication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderPublicationRepository extends JpaRepository<OrderPublication, Long> {
    List<OrderPublication> findByOrderPartId(Long id);
}
