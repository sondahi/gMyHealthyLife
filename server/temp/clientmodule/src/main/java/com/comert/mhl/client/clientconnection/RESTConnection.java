package com.comert.mhl.client.clientconnection;

import javax.naming.InitialContext;

public class RESTConnection extends ClientConnection {

    public RESTConnection(InitialContext context) {
        super(context);
    }

    public static ClientConnection getConnection() {
        InitialContext context = null;
        return new RESTConnection(context);
    }
}
