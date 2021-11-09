package com.comert.mhl.mail.service.ejb;

import com.comert.mhl.mail.mail.Mail;
import com.comert.mhl.mail.mailsender.MessageProducer;
import com.comert.mhl.mail.service.local.MailServiceLocal;
import com.comert.mhl.mail.service.remote.MailServiceRemote;

import jakarta.ejb.Local;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Remote(MailServiceRemote.class)
@Local(MailServiceLocal.class)
@Stateless(name = "MailServiceEJB")
public class MailServiceBean implements MailServiceLocal, MailServiceRemote {

    @Inject
    private MessageProducer messageProducer;

    @Override
    public void sendMail(Mail mail) {
        messageProducer.sendMail(mail);
    }
}
