package com.comert.mhl.payment.service;

import com.comert.mhl.payment.dto.Order;

/*
* Payment gibi ödeme işlemine özel husular payer paketinde yapılacak.
* Frontend'e gidecek ödeme sonucu gibi genel ortak özellikler burada belirtilip bean'de çözmülenecek.
 */
public interface PayerService {

    String getApprovalLink(Order order, String cancelUrl, String successUrl);
    void pay(String paymentId, String payerId);
    void removeEJB();
}
