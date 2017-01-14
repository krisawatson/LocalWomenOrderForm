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
	
	@Autowired
	public AdvertSizeRepository adSizeRepo;
	
	@Autowired
	public AdvertTypeRepository adTypeRepo;
	
	@Autowired
	public PublicationRepository publicationRepo;

	@Autowired
	public RoleRepository roleRepo;

    @RequestMapping(value = "/adtypes", method = RequestMethod.GET)
    public List<AdvertType> getAdvertTypes() {
        LOGGER.debug("Getting the list of advert types");
        List<AdvertType> adTypes = adTypeRepo.findAll();
        return adTypes;
    }
    
    @RequestMapping(value = "/adsizes", method = RequestMethod.GET)
    public List<AdvertSize> getAdvertSizes() {
        LOGGER.debug("Getting the list of advert sizes");
        List<AdvertSize> adSizes = adSizeRepo.findAll();
        return adSizes;
    }
    
    @RequestMapping(value = "/publications", method = RequestMethod.GET)
    public List<Publication> getPublications() {
        LOGGER.debug("Getting the list of publications");
        List<Publication> publications = publicationRepo.findAll();
        return publications;
    }
    
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List<Role> getRoles() {
        LOGGER.debug("Getting the list of roles");
        List<Role> roles = roleRepo.findAll();
        return roles;
    }
}
