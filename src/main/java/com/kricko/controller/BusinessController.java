package com.kricko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.domain.Business;
import com.kricko.service.BusinessService;

@RestController
@RequestMapping("business")
public class BusinessController {

    @Autowired
    BusinessService businessService;
    
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Business getBusiness( @PathVariable(value="id") Long id) {
        return businessService.getBusiness (id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Business> getBusinesses() {
        return businessService.getBusinesses();
    }
    
    @RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
    @Transactional
    public void updateBusiness(@PathVariable(value="id") Long id, @RequestBody Business business) {
        businessService.updateBusiness(id, business);
    }
}
