package com.comert.mhl.payment.payer;

import com.comert.mhl.payment.dto.Order;
import com.paypal.api.payments.Payment;

import jakarta.enterprise.context.ApplicationScoped;

@Bank(bankType = BankType.DEUTSHEBANK)
@ApplicationScoped
public class DeutscheBankPayerBean implements Payer {


    @Override
    public Payment createPayment ( Order order , String cancelUrl , String successUrl ) {
        return null;
    }

    @Override
    public Payment executePayment ( String paymentId , String payerId ) {
        return null;
    }
}
