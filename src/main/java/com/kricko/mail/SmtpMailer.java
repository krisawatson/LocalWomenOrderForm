package com.kricko.mail;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.kricko.constants.EmailType;
import com.kricko.constants.MailTemplating;
import com.kricko.domain.Orders;


@Component
public class SmtpMailer
{
    private final String COPYRIGHT_TEMPLATE = MailTemplating.TMPL_FOLDER + MailTemplating.TMPL_COPYRIGHT;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private VelocityEngine velocityEngine;
    
    private void send(String to, String subject, String template, Map<String, Object> model) throws MessagingException {
        addCommonModelProperties(model);
        
        // Get the template and replace the variables with the model details
        String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
                template, StandardCharsets.UTF_8.toString(), model);
        
        appendCommonContent(body, model);

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setContent(body, "text/html");
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        
        javaMailSender.send(message);
    }
    
    public void sendOrderConfirmation(Long orderId, String to, EmailType type, Orders orders) throws MessagingException {
        Map<String, Object> model = new HashMap<>();
        model.put("orderNumber", orderId);
        model.put("disclaimer", MailTemplating.DISCLAIMER);
        
        /* TODO Build the map of the orders
         * orders: [{
         *      month: "May",
         *      year: 2017
         *      adverts: [{
         *          publication: "My Pub 1",
         *          type: "Photo Shoot",
         *          size: "1/2",
         *          note: "Note 1"
         *      },{
         *          publication: "My Pub 2",
         *          type: "Photo Shoot",
         *          size: "1/2",
         *          note: "Note 2"
         *      }]
         * },{
         *      month: "June",
         *      year: 2017
         *      adverts: [{
         *          publication: "My Pub 1",
         *          type: "Photo Shoot",
         *          size: "1/2",
         *          note: "Note 3"
         *      },{
         *          publication: "My Pub 2",
         *          type: "Photo Shoot",
         *          size: "1/2",
         *          note: "Note 4"
         *      }]
         */
        String subject = "Local Women Advert Order Confirmation";
        String template = MailTemplating.TMPL_ORDER_FOLDER + "/" 
                + type.getValue() + "/" + MailTemplating.TMPL_ORDER_CONFIRMATION;
        send(to,subject, template, model);
    }
    
    private void addCommonModelProperties(Map<String, Object> model) {
        // Adding copyright and year to the model for the copyright
        Calendar calendar = new GregorianCalendar();
        String copyright = MailTemplating.COPYRIGHT.replaceAll("\\$\\{year\\}", Integer.toString(calendar.get(Calendar.YEAR)));
        model.put("copyright", copyright);
    }
    
    private void appendCommonContent(String body, Map<String, Object> model) {
        body += VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
                MailTemplating.TMPL_FOOTER, StandardCharsets.UTF_8.toString(), model);
        body += VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
                MailTemplating.TMPL_TERMS_AND_CONDITIONS, StandardCharsets.UTF_8.toString(), model);
        body += VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
                COPYRIGHT_TEMPLATE, StandardCharsets.UTF_8.toString(), model);
    }
}
