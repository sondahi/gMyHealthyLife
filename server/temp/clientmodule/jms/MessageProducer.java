package com.comert.mhl.database.jms;

import java.io.Serializable;

public interface MessageProducer {
    void send(String text);
    void send(String name, String value, Serializable entity);
}
