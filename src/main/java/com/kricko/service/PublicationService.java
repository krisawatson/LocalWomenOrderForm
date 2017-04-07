package com.kricko.service;

import com.kricko.domain.Publication;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PublicationService {

    List<Publication> getPublications();

    ResponseEntity<Void> createPublication(@RequestBody Publication publication);

    @Transactional
    void updatePublication(Long pubId, Publication publication);
}
