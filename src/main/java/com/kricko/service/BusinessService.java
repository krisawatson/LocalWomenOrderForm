package com.kricko.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kricko.domain.Business;

public interface BusinessService
{

    public List<Business> getBusinesses();
    
    public Business getBusiness(Long id);
    
    @Transactional
    public void updateBusiness(Long id, Business business);
}
