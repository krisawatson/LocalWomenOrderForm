package com.kricko.mail;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.kricko.constants.EmailType;
import com.kricko.domain.Orders;
import com.kricko.domain.Publication;
import com.kricko.repository.PublicationRepository;
import com.kricko.service.MailTemplateService;


@Component
public class SmtpMailer
{
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailTemplateService mailTemplateService;
    @Autowired
    private PublicationRepository pubRepo;
    
    private void send(String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setContent(body, "text/html");
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        
        javaMailSender.send(message);
    }
    
    public void sendOrderConfirmation(String to, EmailType type, Orders orders) throws MessagingException {
    	String subject = "Local Women Order Confirmation";
    	if(EmailType.PUBLICATION == type) {
    		List<Publication> publications = pubRepo.findAll();
	    	for(Publication pub : publications) {
		    	String body = mailTemplateService.buildPublicationTemplate(type, orders, pub.getId());
		    	System.out.println(body);
		        if(null != body) {
		        	send(pub.getEmail(), subject, body);
	    		}
	    	}
    	} else {
    		String body = mailTemplateService.buildTemplate(type, orders);
	        send(to, subject, body);
    	}
    }
}
