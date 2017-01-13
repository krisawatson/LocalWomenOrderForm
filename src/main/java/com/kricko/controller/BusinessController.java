package com.kricko.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.domain.Business;
import com.kricko.repository.BusinessRepository;

@RestController
@RequestMapping("business")
public class BusinessController {

	private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	public BusinessRepository businessRepo;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Business getBusinesses( @PathVariable(value="id") Long id) {
        LOGGER.debug("Getting the details of businesses with id " + id);
        Business business = businessRepo.findOne(id);
        return business;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Business> getBusinesses() {
        LOGGER.debug("Getting the list of businesses");
        List<Business> businesses = businessRepo.findAll();
        return businesses;
    }
}
