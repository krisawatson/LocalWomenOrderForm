package com.kricko.mail;

import com.kricko.constants.EmailType;
import com.kricko.domain.Business;
import com.kricko.domain.Orders;
import com.kricko.domain.Publication;
import com.kricko.repository.PublicationRepository;
import com.kricko.service.MailTemplateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;


@Component
public class SmtpMailer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FROM_ADDRESS = "orders@localwomensnews.com";
    private final JavaMailSender javaMailSender;
    private final MailTemplateService mailTemplateService;
    private final PublicationRepository pubRepo;

    @Autowired
    public SmtpMailer(JavaMailSender javaMailSender, MailTemplateService mailTemplateService,
                      PublicationRepository pubRepo) {
        this.javaMailSender = javaMailSender;
        this.mailTemplateService = mailTemplateService;
        this.pubRepo = pubRepo;
    }

    private void send(String to, String[] cc, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setContent(body, "text/html");
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(FROM_ADDRESS);
        helper.setTo(to);
        if (null != cc) {
            helper.setCc(cc);
        }
        helper.setSubject(subject);
        helper.setText(body, true);

        javaMailSender.send(message);
    }

    public void sendOrderConfirmation(String to, String[] cc, EmailType type, Business business, Orders orders) throws MessagingException {
        LOGGER.debug(orders.toString());
        String subject = "Local Women Order Confirmation";
        if (EmailType.PUBLICATION == type || EmailType.PHOTOSHOOT == type) {
            LOGGER.info("Sending order confirmation for the publications");
            List<Publication> publications = pubRepo.findAll();
            for (Publication pub : publications) {
                String body = mailTemplateService.buildTemplate(type, business, orders, pub.getId());
                if (null != body) {
                    LOGGER.info(String.format("Sending order confirmation for %s %s", pub.getName(), type.getValue()));
                    String email = EmailType.PUBLICATION == type ? pub.getEmail() : pub.getPhotoshootEmail();
                    send(email, null, subject, body);
                }
            }
        } else {
            String body = mailTemplateService.buildTemplate(type, business, orders);
            send(to, cc, subject, body);
        }
    }
}
