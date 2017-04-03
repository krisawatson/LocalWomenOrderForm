package com.kricko.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kricko.constants.EmailType;
import com.kricko.domain.Business;
import com.kricko.domain.Orders;
import com.kricko.mail.SmtpMailer;

public class OrderConfirmationMailer implements Runnable
{
    private static final Logger LOGGER = LogManager.getLogger();

    private final Business business;
    private final Orders orders;
    private final String email;
    private final String[] cc;
    private final EmailType type;
    private final SmtpMailer mailer;

    public OrderConfirmationMailer(SmtpMailer mailer, Business business, Orders orders, String email, String[] cc, EmailType type) {
        this.mailer = mailer;
        this.business = business;
        this.orders = orders;
        this.email = email;
        this.cc = cc;
        this.type = type;
    }
    @Override
    public void run ()
    {
        try {
            mailer.sendOrderConfirmation(email, cc, type, business, orders); 
        } catch (Exception e) {
            LOGGER.error("Failed to send email", e);
        }
    }
}
