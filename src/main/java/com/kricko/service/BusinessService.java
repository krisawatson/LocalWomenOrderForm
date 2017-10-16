/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.service;

import com.kricko.domain.Business;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BusinessService {

    List<Business> getBusinesses();

    Business getBusiness(Long id);

    @Transactional
    void updateBusiness(Long id, Business business);
}
