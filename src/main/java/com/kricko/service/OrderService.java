package com.kricko.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.kricko.constants.EmailType;
import com.kricko.constants.Roles;
import com.kricko.domain.Business;
import com.kricko.domain.OrderPart;
import com.kricko.domain.OrderPublication;
import com.kricko.domain.Orders;
import com.kricko.domain.Role;
import com.kricko.domain.User;
import com.kricko.model.WebOrder;

public interface OrderService
{

	public Orders getOrder(Long id);

    public Long createOrder(WebOrder webOrder, User user);

    public List<Orders> getOrders();
    
    @Transactional
    public void updateOrder(WebOrder webOrder);
}
