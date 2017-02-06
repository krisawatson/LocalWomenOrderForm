package com.kricko.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.kricko.domain.Publication;
import com.kricko.domain.Role;
import com.kricko.domain.User;
import com.kricko.repository.PublicationRepository;

@Service("publicationService")
public class PublicationServiceImpl implements PublicationService
{
    private static final Logger LOGGER = LogManager.getLogger();
    
    @Autowired
    PublicationRepository pubRepo;
    
    @Override
    public List<Publication> getPublications() {
        return pubRepo.findAll();
    }
    
    @Override
    public ResponseEntity<Void> createPublication(@RequestBody Publication publication) {
        try{
            pubRepo.save(publication);
        } catch (Exception e) {
            LOGGER.error("Exception while trying to create a publication", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @Override
    public ResponseEntity<Void> updatePublication(Long pubId, Publication publication) {
    	pubRepo.updatePublicationInfoById(pubId, publication.getName(), publication.getEmail());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
