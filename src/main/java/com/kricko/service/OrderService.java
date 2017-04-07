/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.service;

import com.kricko.domain.Orders;
import com.kricko.domain.User;
import com.kricko.model.WebOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {

    Orders getOrder(Long id);

    Long createOrder(WebOrder webOrder, User user);

    List<Orders> getOrders();

    @Transactional
    void updateOrder(Orders webOrder, User user);

    void removeOrderPart(Long orderId, Long orderPartId);
}
