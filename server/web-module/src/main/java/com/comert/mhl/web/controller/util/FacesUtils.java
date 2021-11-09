package com.comert.mhl.web.controller.util;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Locale;
import java.util.Map;

@Named(value = "facesUtils")
@RequestScoped
public class FacesUtils {

    @Inject
    private FacesContext facesContext;

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public ExternalContext getExternalContext(){ return facesContext.getExternalContext(); }

    public HttpSession getSession(boolean create){ return (HttpSession) getExternalContext().getSession(create); }

    public HttpServletRequest getRequest(){
        return (HttpServletRequest) getExternalContext().getRequest();
    }

    public HttpServletResponse getResponse(){ return (HttpServletResponse) getExternalContext().getResponse();}

    public String getRequestParameter(String parameterName){
        return getRequest().getParameter(parameterName);
    }

    public Object getRequestAttribute(String attributeName){ return getRequest().getAttribute(attributeName); }

    public void setRequestAttribute(String attributeName, Object value){ getRequest().setAttribute(attributeName, value); }

    public Object getSessionAttribute(String attributeName){ return getSession(false).getAttribute(attributeName); }

    public void setSessionAttribute(String attributeName, Object value){ getSession(false).setAttribute(attributeName, value); }

    public void addMessage(String clientId, FacesMessage message){ facesContext.addMessage(clientId, message);}

    public void addMessage(String clientId, FacesMessage.Severity severity, String summery, String detail){
        FacesMessage message = new FacesMessage();
        message.setSeverity(severity);
        message.setSummary(summery);
        message.setDetail(detail);
        addMessage(clientId,message);
    }

    public boolean isMessageListEmpty(){
        return facesContext.getMessageList().isEmpty();
    }

    public Locale getLocale(){
        return facesContext.getViewRoot ( ).getLocale ( );
    }

    public void changeLocale(Locale locale){
        facesContext.getViewRoot ().setLocale ( locale );
    }

    public void addCookie( String name, String value, Map<String,Object> properties ){
        getExternalContext ().addResponseCookie ( name, value, properties );
    }

    public Cookie getCookie(String name){
        return (Cookie) getExternalContext ().getRequestCookieMap ().get ( name );
    }

}
