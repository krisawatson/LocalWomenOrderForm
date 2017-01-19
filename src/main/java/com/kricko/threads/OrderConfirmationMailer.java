package com.kricko.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kricko.constants.EmailType;
import com.kricko.domain.Orders;
import com.kricko.mail.SmtpMailer;

public class OrderConfirmationMailer implements Runnable
{
    private static final Logger LOGGER = LogManager.getLogger();

    private Orders orders;
    private String email;
    private EmailType type;
    private SmtpMailer mailer;
    
    public OrderConfirmationMailer(SmtpMailer mailer, Orders orders, String email, EmailType type) {
        this.mailer = mailer;
        this.orders = orders;
        this.email = email;
        this.type = type;
    }
    @Override
    public void run ()
    {
        try {
            mailer.sendOrderConfirmation(email, type, orders); 
        } catch (Exception e) {
            LOGGER.error("Failed to send email", e);
        }
    }
}
