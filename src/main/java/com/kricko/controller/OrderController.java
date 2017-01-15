package com.kricko.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.constants.EmailType;
import com.kricko.constants.Roles;
import com.kricko.domain.Business;
import com.kricko.domain.OrderPart;
import com.kricko.domain.OrderPublication;
import com.kricko.domain.Orders;
import com.kricko.domain.User;
import com.kricko.mail.SmtpMailer;
import com.kricko.model.WebOrder;
import com.kricko.repository.BusinessRepository;
import com.kricko.repository.OrderPartRepository;
import com.kricko.repository.OrderPublicationRepository;
import com.kricko.repository.OrderRepository;
import com.kricko.repository.PublicationRepository;
import com.kricko.repository.UserRepository;


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
    @Autowired
    PublicationRepository pubRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    SmtpMailer mailer;
    
    Authentication auth;
    

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Long createOrder(@RequestBody WebOrder webOrder) {
        LOGGER.debug("Submitting order");

        Business business = webOrder.getBusiness();
        businessRepo.save(business);
        
        User user = getUser();
        Orders orders = new Orders(business.getId(), user.getId());
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
        Long orderId = orders.getId();
        String businessEmail = business.getEmail();
        try {
			mailer.sendOrderConfirmation(orderId, businessEmail, EmailType.BUSINESS, orders);
			mailer.sendOrderConfirmation(orderId, user.getEmail(), EmailType.USER, orders);
			// TODO Implement sending mail to the publications 
		} catch (MessagingException e) {
			LOGGER.error("Failed to send email", e);
		}
        
    	return orders.getId();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Orders> getOrders() {
        User user = getUser();
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority(Roles.ADMIN.toString()));
        if(isAdmin){
            return orderRepo.findAll();
        } else {
            return orderRepo.findByUserId(user.getId());
        }
    }
    
    private User getUser() {
        auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepo.findByUsername(username);
    }
}