package com.comert.mhl.payment.payer;

import com.comert.mhl.payment.dto.Order;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Default
@ApplicationScoped
public class PayPalPayerBean implements Payer {

    @Inject
    private APIContext apiContext;

    public Payment createPayment(Order order, String cancelUrl, String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(order.getCurrency());
        double total = BigDecimal.valueOf(order.getTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format(Locale.US, "%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(order.getDescription());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        com.paypal.api.payments.Payer payer = new com.paypal.api.payments.Payer();
        payer.setPaymentMethod(order.getMethod());

        Payment payment = new Payment();
        payment.setIntent(order.getIntent());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

}
