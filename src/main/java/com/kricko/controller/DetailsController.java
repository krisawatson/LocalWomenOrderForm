package com.kricko.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.domain.AdvertSize;
import com.kricko.domain.AdvertType;
import com.kricko.domain.Publication;
import com.kricko.domain.Role;
import com.kricko.repository.AdvertSizeRepository;
import com.kricko.repository.AdvertTypeRepository;
import com.kricko.repository.PublicationRepository;
import com.kricko.repository.RoleRepository;

@RestController
@RequestMapping("details")
public class DetailsController {

	private static final Logger LOGGER = LogManager.getLogger();
	
	private final AdvertSizeRepository adSizeRepo;
	
	private final AdvertTypeRepository adTypeRepo;
	
	private final PublicationRepository publicationRepo;

	private final RoleRepository roleRepo;

    @Autowired
    public DetailsController(AdvertSizeRepository adSizeRepo, AdvertTypeRepository adTypeRepo,
                             PublicationRepository publicationRepo, RoleRepository roleRepo) {
        this.adSizeRepo = adSizeRepo;
        this.adTypeRepo = adTypeRepo;
        this.publicationRepo = publicationRepo;
        this.roleRepo = roleRepo;
    }

    @RequestMapping(value = "/adtypes", method = RequestMethod.GET)
    public List<AdvertType> getAdvertTypes() {
        LOGGER.debug("Getting the list of advert types");
        return adTypeRepo.findAll();
    }
    
    @RequestMapping(value = "/adsizes", method = RequestMethod.GET)
    public List<AdvertSize> getAdvertSizes() {
        LOGGER.debug("Getting the list of advert sizes");
        return adSizeRepo.findAll();
    }
    
    @RequestMapping(value = "/publications", method = RequestMethod.GET)
    public List<Publication> getPublications() {
        LOGGER.debug("Getting the list of publications");
        return publicationRepo.findAll();
    }
    
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List<Role> getRoles() {
        LOGGER.debug("Getting the list of roles");
        return roleRepo.findAll();
    }
}
