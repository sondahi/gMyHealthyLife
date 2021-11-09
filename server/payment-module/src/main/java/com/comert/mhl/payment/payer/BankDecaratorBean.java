package com.comert.mhl.payment.payer;

import com.comert.mhl.payment.dto.Order;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;

// ilgili metodları impl edeceğiz. Gerçi örnekte bir tane var ama çoklu durumlar için
@Decorator
public abstract class BankDecaratorBean implements Payer {

    //@Default // üçünüde aynı anda decore edemiyor. Çaresine bakarız
    @Bank(bankType = BankType.SPARKASSE)
    @Inject
    @Delegate
    private Payer decoratedInstance;

    @Override
    public Payment createPayment ( Order order , String cancelUrl , String successUrl ) throws PayPalRESTException {
        return null;
    }

    @Override
    public Payment executePayment ( String paymentId , String payerId ) throws PayPalRESTException {
        return null;
    }
}
