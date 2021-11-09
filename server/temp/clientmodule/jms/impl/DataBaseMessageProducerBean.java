package com.comert.mhl.database.jms.impl;

import com.comert.mhl.database.jms.DataBaseMessageProducerLocal;
import com.comert.mhl.database.jms.DataBaseMessageProducerRemote;

import jakarta.annotation.Resource;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.*;
import java.io.Serializable;

@Stateless(name = "DataBaseMessageProducerEJB")
@Local( DataBaseMessageProducerLocal.class)
@Remote( DataBaseMessageProducerRemote.class)
public class DataBaseMessageProducerBean implements DataBaseMessageProducerLocal, DataBaseMessageProducerRemote, Serializable {

    @Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext context;

    @Resource(lookup = "java:/jms/queue/DATABASEQ")
    private Queue queue;

    public DataBaseMessageProducerBean() {
    }

    @Override
    public void send(String text) { // Senkronizasyon ojbesi olacak. Mesela Meal Record
        context.createProducer().send(queue,text);
    }

    @Override
    public void send(String name, String value, Serializable entity) {
        JMSProducer producer = context.createProducer();
        producer.setProperty(name, value);
        producer.send(queue,entity);
    }

}
