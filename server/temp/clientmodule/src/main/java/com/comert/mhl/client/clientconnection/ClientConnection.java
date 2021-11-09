package com.comert.mhl.client.clientconnection;

import javax.naming.InitialContext;

public abstract class ClientConnection{

    protected final InitialContext context;

    protected ClientConnection(final InitialContext context) {
        this.context = context;
    }


}
