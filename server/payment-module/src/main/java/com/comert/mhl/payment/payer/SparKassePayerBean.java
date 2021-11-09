package com.comert.mhl.payment.payer;

import com.comert.mhl.payment.dto.Order;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import jakarta.enterprise.context.ApplicationScoped;

@Bank(bankType = BankType.SPARKASSE)
@ApplicationScoped
public class SparKassePayerBean implements Payer {


    @Override
    public Payment createPayment ( Order order , String cancelUrl , String successUrl ) throws PayPalRESTException {
        return null;
    }

    @Override
    public Payment executePayment ( String paymentId , String payerId ) throws PayPalRESTException {
        return null;
    }
}
