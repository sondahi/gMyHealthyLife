package com.comert.mhl.mail.mailsender;

import com.comert.mhl.mail.mail.Mail;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;

@Dependent
public class MessageProducerBean implements MessageProducer {

    @Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext context;

    @Resource(lookup = "java:/jms/queue/MAILQ")
    private Queue queue;

    @Override
    public void sendMail(Mail mail) {
        JMSProducer producer = context.createProducer();
        producer.send(queue,mail);
    }

}
