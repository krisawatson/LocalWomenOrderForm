package com.kricko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.domain.Publication;
import com.kricko.service.PublicationService;


@RestController
@RequestMapping(value = "/publication")
public class PublicationController {

	@Autowired
	PublicationService publicationService;
	
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
    public void updatePublication(@PathVariable(value="id") Long pubId, @RequestBody Publication publication) {
		publicationService.updatePublication(pubId, publication);
    }
}
