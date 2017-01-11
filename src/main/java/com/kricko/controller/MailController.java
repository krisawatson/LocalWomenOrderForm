package com.kricko.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kricko.mail.SmtpMailer;


@RestController
public class MailController
{
    @Autowired
    private SmtpMailer smtpMailer;
    
    @RequestMapping
    public void sendMail() throws MessagingException {
        smtpMailer.send("kris.watson@nanthealth.com", "Test from spring boot", "This is a test mail sent from spring boot");
    }
}
