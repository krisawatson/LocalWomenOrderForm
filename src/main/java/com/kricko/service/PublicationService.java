package com.kricko.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.kricko.domain.Publication;

public interface PublicationService
{

    public List<Publication> getPublications();
    
    public ResponseEntity<Void> createPublication(@RequestBody Publication publication);
    
    @Transactional
    public ResponseEntity<Void> updatePublication(Long pubId, Publication publication);
}
