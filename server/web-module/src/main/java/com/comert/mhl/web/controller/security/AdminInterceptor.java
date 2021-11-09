package com.comert.mhl.web.controller.security;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
@Security(rolType = RolType.ADMIN)
public class AdminInterceptor {

    @AroundInvoke
    public Object handle(InvocationContext context){
        Object object = null;
        System.out.println("Check Access");
        Object parameter = context.getParameters()[0];
        RolType rolType = (RolType) parameter;
        if(rolType != RolType.ADMIN){
            return null;
        } else {
            try {
                object = context.proceed();
            } catch (Exception e) {
            }
        }
        return object;
    }

}
