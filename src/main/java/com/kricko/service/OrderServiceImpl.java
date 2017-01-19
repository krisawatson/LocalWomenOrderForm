package com.kricko.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kricko.constants.EmailType;
import com.kricko.constants.MailTemplating;
import com.kricko.constants.Roles;
import com.kricko.domain.Business;
import com.kricko.domain.OrderPart;
import com.kricko.domain.OrderPublication;
import com.kricko.domain.Orders;
import com.kricko.domain.Publication;
import com.kricko.domain.User;
import com.kricko.mail.SmtpMailer;
import com.kricko.model.WebOrder;
import com.kricko.repository.BusinessRepository;
import com.kricko.repository.OrderPartRepository;
import com.kricko.repository.OrderPublicationRepository;
import com.kricko.repository.OrderRepository;
import com.kricko.repository.PublicationRepository;
import com.kricko.repository.UserRepository;
import com.kricko.threads.OrderConfirmationMailer;

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
    
    @Value("${orders.email.account.orders}")
    private String ordersEmail;
    @Value("${orders.email.account.accounts}")
    private String accountsEmail;

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
        List<OrderConfirmationMailer> mails  = new ArrayList<>(0);
        mails.add(new OrderConfirmationMailer(mailer, orders, businessEmail, EmailType.BUSINESS));
        mails.add(new OrderConfirmationMailer(mailer, orders, user.getEmail(), EmailType.USER));
        mails.add(new OrderConfirmationMailer(mailer, orders, ordersEmail, EmailType.ORDERS));
        mails.add(new OrderConfirmationMailer(mailer, orders, accountsEmail, EmailType.ACCOUNTS));
        mails.add(new OrderConfirmationMailer(mailer, orders, null, EmailType.PUBLICATION));
        sendMails(mails);
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
    
    private void sendMails(List<OrderConfirmationMailer> mails) {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(2), 
                                                                 threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        
        for(OrderConfirmationMailer mail : mails){
            executorPool.execute(mail);
        }
        executorPool.shutdown();
    }
    
    private Map<Long, Set<OrderPublication>> getPublicationSet( List<OrderPart> orderParts) {
        Map<Long, Set<OrderPublication>> publicationSet = new HashMap<>(0);
        for(OrderPart orderPart: orderParts) {
            for(OrderPublication orderPub : orderPart.getPublications()){
                if(publicationSet.containsKey(orderPub.getPublicationId())) {
                    Set<OrderPublication> ops = publicationSet.get(orderPub.getPublicationId());
                    ops.add(orderPub);
                } else {
                    Set<OrderPublication> set = new HashSet<>();
                    set.add(orderPub);
                    publicationSet.put(orderPub.getPublicationId(), set);
                }
            }
        }
        return publicationSet;
    }
    
    private void addPubSetToMails(List<OrderConfirmationMailer> mails, Map<Long, Set<OrderPublication>> pubSet) {
        Iterator<Entry <Long, Set <OrderPublication>>> it = pubSet.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, Set<OrderPublication>> pair = it.next();
            Publication pub = pubRepo.findOne(pair.getKey());
            mails.add (new OrderConfirmationMailer(mailer, null, pub.getEmail(), EmailType.PUBLICATION));
        }
    }
}
