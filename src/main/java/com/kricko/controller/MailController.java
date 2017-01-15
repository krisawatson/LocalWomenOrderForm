package com.kricko.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.constants.EmailType;
import com.kricko.mail.SmtpMailer;

@RestController
public class MailController {
	@Autowired
	SmtpMailer mailer;

	@RequestMapping(value = "/mail/send", method = RequestMethod.GET)
    public void sendEmail() {
		try {
			mailer.sendOrderConfirmation(1629L, "treble00_01@hotmail.com", EmailType.BUSINESS, null);
			mailer.sendOrderConfirmation(1629L, "treble00_01@hotmail.com", EmailType.USER, null);
		} catch (MessagingException e) {
			System.err.println(e);
		}
    }
}
