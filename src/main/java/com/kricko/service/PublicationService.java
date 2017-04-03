package com.kricko.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.kricko.domain.Publication;

public interface PublicationService
{

    List<Publication> getPublications();
    
    ResponseEntity<Void> createPublication(@RequestBody Publication publication);
    
    @Transactional
    void updatePublication(Long pubId, Publication publication);
}
