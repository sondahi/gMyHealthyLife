package com.comert.mhl.mail.service.impl;

import com.comert.mhl.mail.mail.Mail;
import com.comert.mhl.mail.mailsender.MessageProducer;
import com.comert.mhl.mail.service.MailService;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Local(MailService.class)
@Stateless(name = "MailServiceEJB")
public class MailServiceImpl implements MailService {

    @Inject
    private MessageProducer messageProducer;

    @Override
    public void sendMail(Mail mail) {
        messageProducer.sendMail(mail);
    }
}
