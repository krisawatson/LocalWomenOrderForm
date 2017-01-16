package com.kricko.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kricko.domain.Business;
import com.kricko.repository.BusinessRepository;

@Service("businessService")
public class BusinessServiceImpl implements BusinessService
{
    private static final Logger LOGGER = LogManager.getLogger();
    
    @Autowired
    public BusinessRepository businessRepo;

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Business getBusiness( @PathVariable(value="id") Long id) {
        LOGGER.debug("Getting the details of businesses with id " + id);
        return businessRepo.findOne(id);
    }

    @Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Business> getBusinesses() {
        LOGGER.debug("Getting the list of businesses");
        return businessRepo.findAll();
    }

    @Override
    public void updateBusiness (Long id, Business business)
    {
        LOGGER.debug("Updating the business " + business.getId());
        businessRepo.updateById(id, business.getName(), business.getFirstname(), business.getLastname(),
                                business.getAddress1(), business.getAddress2(), business.getCity(),
                                business.getCounty(), business.getPostcode(), business.getEmail(),
                                business.getTel());
    }
    
}
