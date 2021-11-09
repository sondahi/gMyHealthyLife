package com.comert.mhl.web.controller.logger;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
@Log(logStatus = LogLevel.WARNING)
public class WarningLogger implements Logger {

    @AroundInvoke
    public Object handle(InvocationContext context){
        Object object = null;
        System.out.println("Before WARNING Logger");
        try {
            object = context.proceed();
            System.out.println("After WARNING Logger");
        } catch (Exception e) {
            System.out.println("WARNING : "+e.getMessage()+" logged..");
        }
        return object;
    }

}
