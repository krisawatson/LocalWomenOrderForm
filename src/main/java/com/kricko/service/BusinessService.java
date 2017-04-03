package com.kricko.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kricko.domain.Business;

public interface BusinessService
{

    List<Business> getBusinesses();
    
    Business getBusiness(Long id);
    
    @Transactional
    void updateBusiness(Long id, Business business);
}
