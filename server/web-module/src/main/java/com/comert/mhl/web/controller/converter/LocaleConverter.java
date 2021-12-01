package com.comert.mhl.web.controller.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

import java.util.Locale;

@FacesConverter(value = "localeConverter")
public class LocaleConverter implements Converter<Locale> {

    @Override
    public Locale getAsObject(FacesContext context, UIComponent component, String value) {
        switch (value) {
            case "en_US":
                return new Locale("en", "US");
            case "de_DE":
                return new Locale("de", "DE");
            case "tr_TR":
                return new Locale("tr", "TR");
            default:
                return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Locale value) {
        return value.toString();
    }
}
