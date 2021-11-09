package com.comert.mhl.web.controller;

import com.comert.mhl.database.common.model.dto.Authentication;
import com.comert.mhl.payment.dto.Order;
import com.comert.mhl.payment.service.local.PayPalServiceLocal;
import com.comert.mhl.web.controller.util.FacesUtils;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.enterprise.inject.New;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/*
* conversion exceptionları kontrol et
* başlamışsa başlatılamaz
* bitirilmişse bitirilemez
* timeout ...
 */
@Named("paymentController")
@ConversationScoped
public class PaymentBean implements Serializable {

    private static final String SUCCESS_URL = "http://localhost:8080/userview/payment/review.xhtml";
    private static final String CANCEL_URL = "http://localhost:8080/userview/payment/receipt.xhtml";

    private FacesUtils facesUtils;
    private Conversation conversation;
    private Order order;

    @EJB
    private PayPalServiceLocal service;

    private String conversionId;

    @Inject
    public PaymentBean(FacesUtils facesUtils, Conversation conversation, @New Order order) {
        this.facesUtils = facesUtils;
        this.conversation = conversation;
        this.order = order;
        order.setTotal(10);
        order.setCurrency("USD");
        order.setMethod("PayPal");
        order.setIntent("sale");
        order.setDescription("TYPE 4 Membership");
        begin();
    }

    @PostConstruct
    public void onConstruct(){
        System.out.println("bean with haschcode : "+hashCode()+" constructed conversionId: "+conversionId);
    }

    @PreDestroy
    public void onDestroy(){
        System.out.println("bean with haschcode : "+hashCode()+" destroyed conversionId: "+conversionId);
        service.removeEJB ();
    }

    private void begin(){
        Authentication authentication = (Authentication) facesUtils.getSessionAttribute("authentication");
        String memberId = String.valueOf(authentication.getMemberId());
        conversation.begin(memberId);
        conversionId = conversation.getId();
    }
    private void end(){
        conversation.end();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String selectProduct(){
        return "/userview/payment/product.xhtml";
    }

    public String checkout(){
        String cancelUrl = CANCEL_URL+"?cid="+conversation.getId();
        String successUrl = SUCCESS_URL+"?cid="+conversation.getId();
        String approvalLink = service.getApprovalLink(order, cancelUrl, successUrl);
        try {
            facesUtils.getExternalContext().redirect(approvalLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/userview/payment/review.xhtml";
    }

    public String pay(){
        String paymentId = getPaymentId();
        String payerId = getPayerId();
        service.pay(paymentId, payerId);
        return "/userview/payment/receipt.xhtml";
    }

    public String goToHome(){
        end();
        return "/userview/home.xhtml";
    }

    public String getPaymentId() {
        return  facesUtils.getRequestParameter("paymentId");
    }

    public String getPayerId() {
        return  facesUtils.getRequestParameter("PayerID");
    }

}
