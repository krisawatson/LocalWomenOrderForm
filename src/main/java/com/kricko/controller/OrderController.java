package com.kricko.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.domain.Business;
import com.kricko.domain.Orders;
import com.kricko.domain.OrderPart;
import com.kricko.domain.OrderPublication;
import com.kricko.model.WebOrder;
import com.kricko.repository.BusinessRepository;
import com.kricko.repository.OrderPartRepository;
import com.kricko.repository.OrderPublicationRepository;
import com.kricko.repository.OrderRepository;


@RestController
public class OrderController {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    BusinessRepository businessRepo;
    @Autowired
    OrderRepository orderRepo;
    @Autowired
    OrderPartRepository orderPartRepo;
    @Autowired
    OrderPublicationRepository orderPublicationRepo;
    

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public int index(@RequestBody WebOrder webOrder) {
        logger.debug("Submitting order");

        Business business = webOrder.getBusiness();
        businessRepo.save(business);
        
        Orders orders = new Orders(business.getId());
        orderRepo.save(orders);
        
        List<OrderPart> orderParts = webOrder.getOrderParts();
        
        orderParts.forEach(orderPart -> {
        	orderPart.setOrders(orders);
        	orderPartRepo.save(orderPart);
        	List<OrderPublication> publications = orderPart.getPublications();
        	publications.forEach(publication -> {
        		publication.setOrderPart(orderPart);
        	});
        	orderPublicationRepo.save(publications);
        });
        
    	return orders.getId();
    }

}