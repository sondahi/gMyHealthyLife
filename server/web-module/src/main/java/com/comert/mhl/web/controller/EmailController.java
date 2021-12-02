package com.comert.mhl.web.controller;

import com.comert.mhl.mail.mail.Mail;
import com.comert.mhl.mail.service.MailService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.New;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named(value = "emailController")
public class EmailController {

    private Mail mail;

    @EJB
    private MailService mailService;

    @Inject
    public EmailController(@New Mail mail) {
        this.mail = mail;
        this.mail.setFrom("sondahi.java@gmail.com");
        this.mail.setTo("gokay.comert@icloud.com");
        this.mail.setSubject("hi");
        this.mail.setContent("Hello World");
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public void sendEmail() {
        mailService.sendMail(mail);
    }
}
