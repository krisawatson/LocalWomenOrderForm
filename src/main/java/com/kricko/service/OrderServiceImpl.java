package com.kricko.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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

import com.kricko.constants.AdvertTypeEnum;
import com.kricko.constants.EmailType;
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

        boolean hasPhotoshoot = false;
        Business business = webOrder.getBusiness();
        businessRepo.saveAndFlush(business);

        Orders orders = new Orders(business.getId(), user.getId(), 
                    webOrder.getPriceExVat(), webOrder.getPriceIncVat(), 
                    webOrder.getDeposit());
        orders.setCreated(new Date(System.currentTimeMillis()));
        orderRepo.saveAndFlush(orders);

        List<OrderPart> orderParts = webOrder.getOrderParts();
        for(OrderPart orderPart : orderParts) {
            LOGGER.debug(String.format("Order Part is %s", orderPart.toString()));
            orderPart.setOrders (orders);
            orderPartRepo.saveAndFlush(orderPart);
            List<OrderPublication> publications = orderPart.getPublications();
            LOGGER.debug(String.format("Publications is not empty for %d %d it contains %d", 
                    orderPart.getMonth(), orderPart.getYear(), orderPart.getPublications().size()));
            for(OrderPublication publication : publications) {
                publication.setOrderPart(orderPart);
                hasPhotoshoot = (publication.getAdType().longValue() == AdvertTypeEnum.PHOTOSHOOT.getValue()) 
                            ? true : hasPhotoshoot;
            }
            orderPublicationRepo.save(publications);
        }
        orders.setOrderParts(orderParts);

        Long orderId = orders.getId();
        String businessEmail = business.getEmail();
        List<OrderConfirmationMailer> mails  = new ArrayList<>(0);
        LOGGER.debug("Order after getOrder is " + getOrder(orderId).toString());
        mails.add(new OrderConfirmationMailer(mailer, business, orders, businessEmail, new String[]{ordersEmail, accountsEmail}, EmailType.BUSINESS));
        mails.add(new OrderConfirmationMailer(mailer, business, orders, user.getEmail(), null, EmailType.USER));
        mails.add(new OrderConfirmationMailer(mailer, business, orders, null, null, EmailType.PUBLICATION));
        if(hasPhotoshoot) {
            mails.add(new OrderConfirmationMailer(mailer, business, orders, null, null, EmailType.PHOTOSHOOT));
        }
        sendMails(mails);
        return orderId;
    }

    @Override
    public List<Orders> getOrders() {
    	return orderRepo.findAll();
    }
    
    public List<Orders> getOrdersRestrictUser() {
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
    	LOGGER.info(String.format("Updating order %s", webOrder.toString()));
        Orders orders = orderRepo.getOne(webOrder.getId());
        orders.setUpdated(new Date(System.currentTimeMillis()));
        orders.setPriceExVat(webOrder.getPriceExVat());
        orders.setPriceIncVat(webOrder.getPriceIncVat());
        orders.setDeposit(webOrder.getDeposit());
        List<OrderPart> webOrderParts = webOrder.getOrderParts();
        updateOrderParts(webOrderParts, orders);
        orderRepo.saveAndFlush(orders);
    }

    @Override
    public void removeOrderPart(Long orderId, Long orderPartId) {
        Orders orders = orderRepo.findOne(orderId);
        orders.setUpdated(new Date(System.currentTimeMillis()));
        OrderPart orderPart = orderPartRepo.findOne(orderPartId);
        if(orders.getOrderParts().contains(orderPart)) {
            orderPartRepo.delete(orderPartId);
        }
        orderRepo.saveAndFlush(orders);
    }

    private User getUser() {
        auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepo.findByUsername(username);
    }

    private void updateOrderParts(List<OrderPart> webOrderParts, Orders orders) {
    	for(OrderPart orderPart : webOrderParts) {
    		LOGGER.info("Updating order part", orderPart);
            if(null != orderPart.getId () && orderPartRepo.exists(orderPart.getId())) {
                LOGGER.debug ("Order part exists, updating with new values");
                OrderPart dbOrderPart = orderPartRepo.getOne(orderPart.getId());
                updateOrderPart(dbOrderPart, orderPart);
                updateOrderPublication(orderPart);
                orderPartRepo.saveAndFlush(dbOrderPart);
            } else {
            	orderPart.setOrders(orders);
                LOGGER.debug ("Order part does not exists, creating new");
                updateOrderPublication(orderPart);
                orderPartRepo.saveAndFlush(orderPart);
            }
        }
    }

    private void updateOrderPart(OrderPart dbOrderPart, OrderPart webOrderPart) {
    	LOGGER.info(String.format("Updating order part details from %s to %s", dbOrderPart.toString(), webOrderPart.toString()));
        dbOrderPart.setMonth(webOrderPart.getMonth());
        dbOrderPart.setYear(webOrderPart.getYear());
        dbOrderPart.setPublications(webOrderPart.getPublications());
    }

    private void updateOrderPublication(OrderPart orderPart) {
        List<OrderPublication> webOrderPublications = orderPart.getPublications();
        for(OrderPublication orderPub : webOrderPublications) {
            if(null != orderPub.getId() && orderPublicationRepo.exists(orderPub.getId())) {
                LOGGER.debug ("Order publication exists, updating with new values");
                OrderPublication dbOrderPub = orderPublicationRepo.findOne(orderPub.getId());
                LOGGER.info(String.format("Updating order publication details from %s to %s", dbOrderPub.toString(), orderPub.toString()));
                dbOrderPub.setAdSize(orderPub.getAdSize());
                dbOrderPub.setAdType(orderPub.getAdType());
                dbOrderPub.setNote(orderPub.getNote());
                dbOrderPub.setPublicationId(orderPub.getPublicationId());
                orderPublicationRepo.save(dbOrderPub);
            } else {
                LOGGER.debug ("Order publication does not exists, cerating new");
                orderPub.setOrderPart(orderPart);
                orderPublicationRepo.save(orderPub);
            }
        }
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
}
