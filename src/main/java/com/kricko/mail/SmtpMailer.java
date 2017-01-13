package com.kricko.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class SmtpMailer
{
    @Autowired
    private JavaMailSender javaMailSender;
    
    private void send(String to, String subject, String body, String[] bcc) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setContent(body, "text/html");
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setTo(to);
        helper.setBcc(bcc);
        helper.setSubject(subject);
        helper.setText(body, true);
        
        javaMailSender.send(message);
    }
    
    public void sendOrderConfirmation(Long orderId, String to, String... bcc) throws MessagingException {
    	String subject = "Local Women Advert Order Confirmation";
    	String body = "<h2>Order Completed</h2>";
        body += "<h3>Order Number: " + orderId + "</h3>";
        send(to,subject, body, bcc);
    }
}
