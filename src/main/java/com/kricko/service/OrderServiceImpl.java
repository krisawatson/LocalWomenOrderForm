package com.kricko.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

@Service("orderService")
public class OrderServiceImpl implements OrderService
{
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

    @Override
    public Orders getOrder(Long id) {
    	return orderRepo.findOne(id);
    }

    @Override
    public Long createOrder(WebOrder webOrder, User user) {
        LOGGER.debug("Submitting order");

        Business business = webOrder.getBusiness();
        businessRepo.save(business);
        
        Orders orders = new Orders(business.getId(), user.getId());
        orderRepo.save(orders);
        
        List<OrderPart> orderParts = webOrder.getOrderParts();
        
        // For Java 1.7 and before
        for(OrderPart orderPart : orderParts) {
            orderPart.setOrders (orders);
            orderPartRepo.save(orderPart);
            List<OrderPublication> publications = orderPart.getPublications();
            for(OrderPublication publication : publications) {
                publication.setOrderPart(orderPart);
            }
            orderPublicationRepo.save(publications);
        }
        Long orderId = orders.getId();
        String businessEmail = business.getEmail();
        try {
			mailer.sendOrderConfirmation(orderId, businessEmail, EmailType.BUSINESS, orders);
			mailer.sendOrderConfirmation(orderId, user.getEmail(), EmailType.USER, orders);
			// TODO Implement sending mail to the publications 
		} catch (Exception e) {
			LOGGER.error("Failed to send email", e);
			// Catching the exception so order doesn't fail
		}
        
    	return orderId;
    }

    @Override
    public List<Orders> getOrders() {
        User user = getUser();
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority(Roles.ADMIN.toString()));
        if(isAdmin){
            return orderRepo.findAll();
        } else {
            return orderRepo.findByUserId(user.getId());
        }
    }
    
    @Override
    public void updateOrder(Orders webOrder, User user) {
        Orders orders = orderRepo.getOne(webOrder.getId());
    	List<OrderPart> webOrderParts = webOrder.getOrderParts();
    	updateOrderParts(webOrderParts, orders);
    }
    
    @Override
    public void removeOrderPart(Long orderId, Long orderPartId) {
        Orders order = orderRepo.findOne(orderId);
        OrderPart orderPart = orderPartRepo.findOne(orderPartId);
        if(order.getOrderParts().contains(orderPart)) {
            orderPartRepo.delete(orderPartId);
        }
    }
    
    private User getUser() {
        auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepo.findByUsername(username);
    }
    
    private void updateOrderParts(List<OrderPart> webOrderParts, Orders orders) {
        for(OrderPart orderPart : webOrderParts) {
            orderPart.setOrders(orders);
            if(null != orderPart.getId () && orderPartRepo.exists(orderPart.getId())) {
                LOGGER.debug ("Order part exists, updating with new values");
                OrderPart dbOrderPart = orderPartRepo.getOne(orderPart.getId());
                updateOrderPart(dbOrderPart, orderPart);
                updateOrderPublication(dbOrderPart);
            } else {
                LOGGER.debug ("Order part does not exists, creating new");
                saveOrderPart(orderPart);
                updateOrderPublication(orderPart);
            }
        }
    }
    
    private void updateOrderPart(OrderPart dbOrderPart, OrderPart webOrderPart) {
        dbOrderPart.setMonth(webOrderPart.getMonth());
        dbOrderPart.setYear(webOrderPart.getYear());
        dbOrderPart.setPublications(webOrderPart.getPublications());
        saveOrderPart(dbOrderPart);
    }
    
    private void updateOrderPublication(OrderPart orderPart) {
        List<OrderPublication> webOrderPublications = orderPart.getPublications();
        for(OrderPublication orderPub : webOrderPublications) {
            if(null != orderPub.getId() && orderPublicationRepo.exists(orderPub.getId())) {
                LOGGER.debug ("Order publication exists, updating with new values");
                OrderPublication dbOrderPub = orderPublicationRepo.findOne(orderPub.getId());
                dbOrderPub.setOrderPart(orderPart);
                dbOrderPub.setAdSize(orderPub.getAdSize());
                dbOrderPub.setAdType(orderPub.getAdType());
                dbOrderPub.setNote(orderPub.getNote());
                dbOrderPub.setOrderPart(orderPart);
                dbOrderPub.setPublicationId(orderPub.getPublicationId());
                saveOrderPublication(dbOrderPub);
            } else {
                LOGGER.debug ("Order publication does not exists, cerating new");
                orderPub.setOrderPart(orderPart);
                saveOrderPublication(orderPub);
            }
        }
    }
    
    private void saveOrderPart(OrderPart orderPart) {
        orderPartRepo.save(orderPart);
    }
    
    private void saveOrderPublication(OrderPublication orderPub) {
        orderPublicationRepo.save(orderPub);
    }
}
