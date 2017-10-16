/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.repository;

import com.kricko.domain.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
