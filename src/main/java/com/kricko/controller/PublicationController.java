/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.controller;

import com.kricko.domain.Publication;
import com.kricko.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/publication")
public class PublicationController {

    private final
    PublicationService publicationService;

    @Autowired
    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Publication> getPublications() {
        return publicationService.getPublications();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Void> createPublication(@RequestBody Publication publication) {
        return publicationService.createPublication(publication);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
    @Transactional
    public void updatePublication(@PathVariable(value = "id") Long pubId, @RequestBody Publication publication) {
        publicationService.updatePublication(pubId, publication);
    }
}
