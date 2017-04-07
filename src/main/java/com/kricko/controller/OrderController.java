/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.controller;

import com.kricko.domain.Orders;
import com.kricko.domain.User;
import com.kricko.model.WebOrder;
import com.kricko.service.OrderService;
import com.kricko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("order")
public class OrderController {

    private final
    OrderService orderService;
    private final
    UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Orders getOrder(@PathVariable(value = "id") Long id) {
        return orderService.getOrder(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Long createOrder(@RequestBody WebOrder webOrder) {
        return orderService.createOrder(webOrder, getUser());
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Orders> getOrders() {
        return orderService.getOrders();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateOrder(@PathVariable(value = "id") Long id, @RequestBody Orders webUpdateOrder) {
        orderService.updateOrder(webUpdateOrder, getUser());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{orderId}/orderpart/{orderPartId}", method = RequestMethod.DELETE)
    public void getOrder(@PathVariable(value = "orderId") Long orderId, @PathVariable(value = "orderPartId") Long orderPartId) {
        orderService.removeOrderPart(orderId, orderPartId);
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userService.getUserByUsername(username);
    }
}