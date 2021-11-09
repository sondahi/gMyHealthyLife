package com.comert.mhl.database.common.model.dto;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseMessage {

    private final String message;
    private final String property;

    public ResponseMessage(String message, String property) {
        this.message = message;
        this.property = property;
    }

    public String getMessage() {
        return message;
    }

    public String getProperty() {
        return property;
    }
}
