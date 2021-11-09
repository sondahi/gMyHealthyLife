package com.comert.mhl.mail.mailsender;

import com.comert.mhl.mail.mail.Mail;

import jakarta.annotation.Resource;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;
import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Date;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/MAILQ")
        }
)
public class MessageConsumer implements MessageListener {

    @Resource(mappedName="java:/mail/gmail")
    private Session mailSession;

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            Mail mail = (Mail) objectMessage.getObject();
            MimeMessage mimeMessage = new MimeMessage(mailSession);
            Address fromAddress = new InternetAddress(mail.getFrom());
            mimeMessage.setFrom(fromAddress);
            Address toAddress= new InternetAddress(mail.getTo());
            mimeMessage.setRecipient(jakarta.mail.Message.RecipientType.TO,toAddress);
            mimeMessage.setSubject(mail.getSubject());
            mimeMessage.setSentDate(new Date());
            mimeMessage.setContent(mail.getContent(),"text/plain");
            Transport.send(mimeMessage);
        } catch (JMSException | MessagingException e) {
            e.printStackTrace();
        }
    }

}
