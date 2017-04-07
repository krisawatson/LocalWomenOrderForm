package com.kricko.controller;

import com.kricko.domain.Business;
import com.kricko.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("business")
public class BusinessController {

    private final
    BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Business getBusiness(@PathVariable(value = "id") Long id) {
        return businessService.getBusiness(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Business> getBusinesses() {
        return businessService.getBusinesses();
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
    @Transactional
    public void updateBusiness(@PathVariable(value = "id") Long id, @RequestBody Business business) {
        businessService.updateBusiness(id, business);
    }
}
