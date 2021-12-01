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
    private void init() {
        contextPath = facesUtils.getExternalContext().getRequestContextPath();
    }

    public String forwardHome() {
        return contextPath + "/userview/home.xhtml";
    }

    public String forwardFood() {
        return contextPath + "/userview/food.xhtml";
    }

    public String forwardFoodCategory() {
        return contextPath + "/userview/foodcategory.xhtml";
    }

    public String forwardPayment() {
        return contextPath + "/userview/payment.xhtml";
    }

    public String forwardEmail() {
        return contextPath + "/userview/email.xhtml";
    }


}
