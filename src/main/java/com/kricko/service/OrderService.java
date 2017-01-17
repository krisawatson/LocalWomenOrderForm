package com.kricko.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kricko.domain.Orders;
import com.kricko.domain.User;
import com.kricko.model.WebOrder;

public interface OrderService
{

	public Orders getOrder(Long id);

    public Long createOrder(WebOrder webOrder, User user);

    public List<Orders> getOrders();
    
    @Transactional
    public void updateOrder(Orders webOrder, User user);
    
    public void removeOrderPart(Long orderId, Long orderPartId);
}
