package com.comert.mhl.payment.payer;

import com.paypal.base.rest.APIContext;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

public class PayPalConfig {

    private static final String mode = "sandbox";
    private static final String clientId = "ATuwGwI-i7RBTrEgrnu5MGztdd9-MUg5uk4QlDRxHa2xsEBAAN3b2qrbIfniL-10mmt2dXk8xY95Ibj5";
    private static final String secretId = "ECu8M7fXNkHorh3ZLfaeIJdlFX87wbssekhbxITQ5PIR53k93C0OS4USawzN6rpdPGw23Jv2DnDi2-1K";

    @ApplicationScoped
    @Produces
    public APIContext getAPIContext(){
        return new APIContext(clientId, secretId, mode);
    }

}
