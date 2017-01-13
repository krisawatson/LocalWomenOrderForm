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
import com.kricko.domain.OrderPart;
import com.kricko.domain.OrderPublication;
import com.kricko.domain.Orders;
import com.kricko.model.WebOrder;
import com.kricko.repository.BusinessRepository;
import com.kricko.repository.OrderPartRepository;
import com.kricko.repository.OrderPublicationRepository;
import com.kricko.repository.OrderRepository;


@RestController
@RequestMapping("order")
public class OrderController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    BusinessRepository businessRepo;
    @Autowired
    OrderRepository orderRepo;
    @Autowired
    OrderPartRepository orderPartRepo;
    @Autowired
    OrderPublicationRepository orderPublicationRepo;
    

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Long createOrder(@RequestBody WebOrder webOrder) {
        LOGGER.debug("Submitting order");

        Business business = webOrder.getBusiness();
        businessRepo.save(business);
        
        // TODO Set the userId of the logged in user
        Orders orders = new Orders(business.getId(), 1L);
        orderRepo.save(orders);
        
        List<OrderPart> orderParts = webOrder.getOrderParts();
        
        // For Java 1.8 and after
        orderParts.forEach(orderPart -> {
            orderPart.setOrders(orders);
            orderPartRepo.save(orderPart);
            List<OrderPublication> publications = orderPart.getPublications();
            publications.forEach(publication -> {
                publication.setOrderPart(orderPart);
            });
            orderPublicationRepo.save(publications);
        });
        // For Java 1.7 and before
//        for(OrderPart orderPart : orderParts) {
//            orderPart.setOrders (orders);
//            orderPartRepo.save(orderPart);
//            List<OrderPublication> publications = orderPart.getPublications();
//            for(OrderPublication publication : publications) {
//                publication.setOrderPart(orderPart);
//            }
//            orderPublicationRepo.save(publications);
//        }
        
    	return orders.getId();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Orders getOrders() {
    	return orderRepo.findOne(1L);
    }
}