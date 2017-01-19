package com.kricko.controller;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.constants.EmailType;
import com.kricko.domain.Orders;
import com.kricko.mail.SmtpMailer;
import com.kricko.service.MailTemplateService;
import com.kricko.service.OrderService;

@RestController
public class MailController {
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	SmtpMailer mailer;
	@Autowired
	OrderService orderService;
	@Autowired
	MailTemplateService mailTemplateService;

	@RequestMapping(value = "/mail/test", method = RequestMethod.GET)
    public String testEmail() {
		Orders orders = getSingleOrder(4L);
		String body = mailTemplateService.buildTemplate(EmailType.BUSINESS, orders);
		try {
			mailer.sendOrderConfirmation(null, EmailType.PUBLICATION, orders);
		} catch (MessagingException e) {
			LOGGER.error("Failed to send email", e);
		}
		return body;
    }
	
	private Orders getSingleOrder(Long id) {
		return orderService.getOrder(id);
	}
}
