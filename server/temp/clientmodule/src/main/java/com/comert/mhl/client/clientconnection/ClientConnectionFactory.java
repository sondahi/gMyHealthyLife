package com.comert.mhl.client.clientconnection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public abstract class ClientConnectionFactory{

    private static final String SERVICE_CLIENT_EJB_JNDI_PREFIX = "java:app/DataBase/";
    private static final String REMOTE_CLIENT_EJB_JNDI_PREFIX = "ejb:MHL/DataBase/";
    private static final InitialContext EJB_INITIAL_CONTEX ;

    static {
        Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        InitialContext temp = null;
        try {
            temp = new InitialContext(jndiProperties);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        EJB_INITIAL_CONTEX = temp;
    }

    public static ClientConnection createConnection(ClientConnectionMod clientConnectionMod, ClientConnectionType clientConnectionType) throws NamingException {
        switch (clientConnectionMod) {
            case SERVICE_CLIENT:
                switch (clientConnectionType) {
                    case EJB:
                        return new EJBConnection(EJB_INITIAL_CONTEX, SERVICE_CLIENT_EJB_JNDI_PREFIX);
                    case JMS:
                        return JMSConnection.getConnection();
                    default:
                        return RESTConnection.getConnection();
                }
            default:
                switch (clientConnectionType) {
                    case EJB:
                        return new EJBConnection(EJB_INITIAL_CONTEX, REMOTE_CLIENT_EJB_JNDI_PREFIX);
                    case JMS:
                        return JMSConnection.getConnection();
                    default:
                        return RESTConnection.getConnection();
                }
        }
    }

}
