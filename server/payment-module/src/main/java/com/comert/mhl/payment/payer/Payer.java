package com.comert.mhl.payment.payer;

import com.comert.mhl.payment.dto.Order;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.io.Serializable;

/*
* tüm ödeme yöntemleri için Payment gibi ortak bir class tasarlanacak.
 */
public interface Payer extends Serializable {

    Payment createPayment( Order order, String cancelUrl, String successUrl) throws PayPalRESTException;
    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

}
