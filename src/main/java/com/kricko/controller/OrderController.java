package com.kricko.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.model.Order;

@RestController
public class OrderController {

    private static Logger logger = LogManager.getLogger();

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public int index(@RequestBody Order order) {
        logger.debug("Submitting order");
        // TODO Take the order and write to a database then return an order number
    	return 100045;
    }

}