package com.comert.mhl.web.controller;

import com.comert.mhl.web.controller.util.FacesUtils;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;

@Named(value = "linkController")
@ApplicationScoped
public class LinksBean {

    @Inject
    private FacesUtils facesUtils;

    private String contextPath;

    @PostConstruct
    private void init(){
        contextPath = facesUtils.getExternalContext().getRequestContextPath();
    }

    public void logout() throws IOException {
        facesUtils.getSession(false).invalidate();
        facesUtils.getResponse().sendRedirect(forwarIndex());
    }

    public String forwardHome(){
        return contextPath + "/userview/home.xhtml";// navi ile redirect yapılacak diğerlerinde forward
    }

    public String forwardPayPal(){
        return contextPath + "/userview/payment/paymentmethod.xhtml";
    }

    public String forwarIndex(){
        return contextPath + "/indexview/index.xhtml?faces-redirect=true";
    }

    public String forwardFood(){
        return contextPath + "/userview/food.xhtml";
    }

    public String forwardTest(){
        return contextPath + "/userview/test.xhtml";
    }

}
