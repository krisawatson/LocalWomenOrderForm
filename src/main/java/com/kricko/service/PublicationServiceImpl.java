/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.service;

import com.kricko.domain.Publication;
import com.kricko.repository.PublicationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service("publicationService")
public class PublicationServiceImpl implements PublicationService {
    private static final Logger LOGGER = LogManager.getLogger();

    private final
    PublicationRepository pubRepo;

    @Autowired
    public PublicationServiceImpl(PublicationRepository pubRepo) {
        this.pubRepo = pubRepo;
    }

    @Override
    public List<Publication> getPublications() {
        return pubRepo.findAll();
    }

    @Override
    public ResponseEntity<Void> createPublication(@RequestBody Publication publication) {
        try {
            pubRepo.save(publication);
        } catch (Exception e) {
            LOGGER.error("Exception while trying to create a publication", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public void updatePublication(Long pubId, Publication publication) {
        Publication dbPublication = pubRepo.findOne(pubId);
        dbPublication.setEmail(publication.getEmail());
        dbPublication.setEnabled(publication.isEnabled());
        dbPublication.setName(publication.getName());
        dbPublication.setPhotoshootEmail(publication.getPhotoshootEmail());

        pubRepo.saveAndFlush(dbPublication);
        new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
