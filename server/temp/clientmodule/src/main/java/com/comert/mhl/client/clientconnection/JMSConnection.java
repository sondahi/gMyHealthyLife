package com.comert.mhl.client.clientconnection;

import jakarta.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.Properties;
import java.util.Queue;

public class JMSConnection extends ClientConnection {

    public final static String JMS_CONNECTION_FACTORY_JNDI="jms/RemoteConnectionFactory";
    public final static String JMS_QUEUE_JNDI="jms/queue/DATABASEQ";
    public final static String JMS_USERNAME="jmsuser";       //  The role for this user is "guest" in ApplicationRealm
    public final static String JMS_PASSWORD="gCw183238!";
    public final static String WILDFLY_REMOTING_URL="http-remoting://localhost:8080";

    private QueueConnectionFactory queueConnectionFactory;
    private QueueConnection queueConnection;
    private QueueSession queueSession;
    private Queue queue;
    private MessageProducer messageProducer;
    private TextMessage textMessage;

    private JMSConnection(InitialContext contex) {
        super(contex);
    }

    public static JMSConnection getConnection() throws NamingException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        props.put(Context.PROVIDER_URL, WILDFLY_REMOTING_URL);
        props.put(Context.SECURITY_PRINCIPAL, JMS_USERNAME);
        props.put(Context.SECURITY_CREDENTIALS, JMS_PASSWORD);
        props.put("jboss.naming.client.ejb.context", true);
        InitialContext context = new InitialContext(props);
        return new JMSConnection(context);
    }

    public void open() throws NamingException, JMSException {
        queueConnectionFactory = (QueueConnectionFactory) context.lookup(JMS_CONNECTION_FACTORY_JNDI);
        queueConnection = queueConnectionFactory.createQueueConnection(JMS_USERNAME,JMS_PASSWORD);
        queueSession = queueConnection.createQueueSession(false,QueueSession.AUTO_ACKNOWLEDGE); // transaction yok, haber verme yok yani mesaj alınmış sayılacak.
        queue = (Queue) context.lookup(JMS_QUEUE_JNDI);
        messageProducer = queueSession.createProducer((Destination) queue);
        queueConnection.start();

    }

    public void send(String message){
        try {
            textMessage = queueSession.createTextMessage(message);
            messageProducer.send(textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    public void send(Serializable object){
        try {
            ObjectMessage message = queueSession.createObjectMessage(object);
            messageProducer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void send(String name, String value, Serializable entity) {
        try {
            ObjectMessage message = queueSession.createObjectMessage(entity);
            message.setObjectProperty(name,value);
            messageProducer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            messageProducer.close();
            queueConnection.stop();
            queueSession.close();
            queueConnection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
