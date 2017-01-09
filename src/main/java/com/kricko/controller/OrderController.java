package com.kricko.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.model.Order;

@RestController
public class OrderController {

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public int index(@RequestBody Order order) {
        // TODO Take the order and write to a database then return an order number
    	return 100045;
    }

}