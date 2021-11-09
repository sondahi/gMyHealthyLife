package com.comert.mhl.payment.service.ejb;

import com.comert.mhl.payment.dto.Order;
import com.comert.mhl.payment.payer.Payer;
import com.comert.mhl.payment.service.local.PayPalServiceLocal;
import com.comert.mhl.payment.service.remote.PayPalServiceRemote;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.*;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

@Remote(value = PayPalServiceRemote.class)
@Local(value = PayPalServiceLocal.class)
@Stateful (name = "PayPalServiceEJB")
public class PayPalServiceBean implements PayPalServiceLocal, PayPalServiceRemote {

    private Payment payment;

    @Inject
    @Default
    private Payer payer;

    @PostConstruct
    private void onConstruct(){
        payment = new Payment (  );
    }

    @Override
    public String getApprovalLink(Order order, String cancelUrl, String successUrl) {
        try {
            payment = payer.createPayment(order, cancelUrl, successUrl);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void pay(String paymentId, String payerId) {
        try {
            payment = payer.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                System.out.println("ödeme ok");
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
    }

    @Remove
    @Override
    public void removeEJB ( ) {
    }
}
