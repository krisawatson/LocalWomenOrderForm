/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.service;

import com.kricko.constants.AdvertTypeEnum;
import com.kricko.constants.EmailType;
import com.kricko.domain.*;
import com.kricko.mail.SmtpMailer;
import com.kricko.model.WebOrder;
import com.kricko.repository.*;
import com.kricko.threads.OrderConfirmationMailer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger();

    private final
    BusinessRepository businessRepo;
    private final
    OrderRepository orderRepo;
    private final
    OrderPartRepository orderPartRepo;
    private final
    OrderPublicationRepository orderPublicationRepo;
    private final
    UserRepository userRepo;
    private final
    SmtpMailer mailer;

    @Value("${orders.email.account.orders}")
    private String ordersEmail;
    @Value("${orders.email.account.accounts}")
    private String accountsEmail;

    @Autowired
    public OrderServiceImpl(BusinessRepository businessRepo, OrderRepository orderRepo,
                            OrderPartRepository orderPartRepo, OrderPublicationRepository orderPublicationRepo,
                            UserRepository userRepo, SmtpMailer mailer) {
        this.businessRepo = businessRepo;
        this.orderRepo = orderRepo;
        this.orderPartRepo = orderPartRepo;
        this.orderPublicationRepo = orderPublicationRepo;
        this.userRepo = userRepo;
        this.mailer = mailer;
    }

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
                webOrder.getDeposit(), webOrder.getCustomerSignature(),
                webOrder.getUserSignature());
        orders.setCreated(new Date(System.currentTimeMillis()));
        orderRepo.saveAndFlush(orders);

        List<OrderPart> orderParts = webOrder.getOrderParts();
        for (OrderPart orderPart : orderParts) {
            LOGGER.debug(String.format("Order Part is %s", orderPart.toString()));
            orderPart.setOrders(orders);
            orderPartRepo.saveAndFlush(orderPart);
            List<OrderPublication> publications = orderPart.getPublications();
            LOGGER.debug(String.format("Publications is not empty for %d %d it contains %d",
                    orderPart.getMonth(), orderPart.getYear(), orderPart.getPublications().size()));
            for (OrderPublication publication : publications) {
                publication.setOrderPart(orderPart);
                hasPhotoshoot = (publication.getAdType() == AdvertTypeEnum.PHOTOSHOOT.getValue()) || hasPhotoshoot;
            }
            orderPublicationRepo.save(publications);
        }
        orders.setOrderParts(orderParts);

        Long orderId = orders.getId();
        String businessEmail = business.getEmail();
        List<OrderConfirmationMailer> mails = new ArrayList<>(0);
        LOGGER.debug("Order after getOrder is " + getOrder(orderId).toString());
        LOGGER.debug("Sending to business email " + businessEmail);
        LOGGER.debug("Sending to orders email " + ordersEmail);
        LOGGER.debug("Sending to accounts email " + accountsEmail);
        mails.add(new OrderConfirmationMailer(mailer, business, orders, businessEmail, new String[]{ordersEmail, accountsEmail}, EmailType.BUSINESS));
        mails.add(new OrderConfirmationMailer(mailer, business, orders, user.getEmail(), null, EmailType.USER));
        mails.add(new OrderConfirmationMailer(mailer, business, orders, null, null, EmailType.PUBLICATION));
        if (hasPhotoshoot) {
            mails.add(new OrderConfirmationMailer(mailer, business, orders, null, null, EmailType.PHOTOSHOOT));
        }
        sendMails(mails);
        return orderId;
    }

    @Override
    public List<Orders> getOrders() {
        return orderRepo.findAll();
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
        if (orders.getOrderParts().contains(orderPart)) {
            orderPartRepo.delete(orderPartId);
        }
        orderRepo.saveAndFlush(orders);
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepo.findByUsername(username);
    }

    private void updateOrderParts(List<OrderPart> webOrderParts, Orders orders) {
        for (OrderPart orderPart : webOrderParts) {
            LOGGER.info("Updating order part", orderPart);
            if (null != orderPart.getId() && orderPartRepo.exists(orderPart.getId())) {
                LOGGER.debug("Order part exists, updating with new values");
                OrderPart dbOrderPart = orderPartRepo.getOne(orderPart.getId());
                updateOrderPart(dbOrderPart, orderPart);
                updateOrderPublication(orderPart);
                orderPartRepo.saveAndFlush(dbOrderPart);
            } else {
                orderPart.setOrders(orders);
                LOGGER.debug("Order part does not exists, creating new");
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
        for (OrderPublication orderPub : webOrderPublications) {
            if (null != orderPub.getId() && orderPublicationRepo.exists(orderPub.getId())) {
                LOGGER.debug("Order publication exists, updating with new values");
                OrderPublication dbOrderPub = orderPublicationRepo.findOne(orderPub.getId());
                LOGGER.info(String.format("Updating order publication details from %s to %s", dbOrderPub.toString(), orderPub.toString()));
                dbOrderPub.setAdSize(orderPub.getAdSize());
                dbOrderPub.setAdType(orderPub.getAdType());
                dbOrderPub.setNote(orderPub.getNote());
                dbOrderPub.setPublicationId(orderPub.getPublicationId());
                orderPublicationRepo.save(dbOrderPub);
            } else {
                LOGGER.debug("Order publication does not exists, cerating new");
                orderPub.setOrderPart(orderPart);
                orderPublicationRepo.save(orderPub);
            }
        }
    }

    private void sendMails(List<OrderConfirmationMailer> mails) {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());

        for (OrderConfirmationMailer mail : mails) {
            executorPool.execute(mail);
        }
        executorPool.shutdown();
    }
}
