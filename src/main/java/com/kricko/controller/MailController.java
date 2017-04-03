package com.kricko.controller;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.constants.EmailType;
import com.kricko.domain.Business;
import com.kricko.domain.Orders;
import com.kricko.mail.SmtpMailer;
import com.kricko.service.BusinessService;
import com.kricko.service.MailTemplateService;
import com.kricko.service.OrderService;

@RestController
public class MailController {
	private static final Logger LOGGER = LogManager.getLogger();
	
	private final
	SmtpMailer mailer;

	private final
	OrderService orderService;

	private final
	BusinessService businessService;

	private final
	MailTemplateService mailTemplateService;

	@Autowired
	public MailController(SmtpMailer mailer, OrderService orderService,
						  BusinessService businessService, MailTemplateService mailTemplateService) {
		this.mailer = mailer;
		this.orderService = orderService;
		this.businessService = businessService;
		this.mailTemplateService = mailTemplateService;
	}

	@RequestMapping(value = "/mail/test", method = RequestMethod.GET)
    public String testEmail() {
		Orders orders = orderService.getOrder(1L);
		Business business = getBusiness(orders.getBusinessId());
		String body = mailTemplateService.buildTemplate(EmailType.BUSINESS, business, orders);
		try {
			mailer.sendOrderConfirmation(null, null, EmailType.PUBLICATION, business, orders);
		} catch (MessagingException e) {
			LOGGER.error("Failed to send email", e);
		}
		return body;
    }
	
	private Business getBusiness(Long id) {
        return businessService.getBusiness(id);
    }
}
