package com.comert.mhl.web.controller.logger;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
@Log(logStatus = LogLevel.TRACE)
public class TraceLogger implements Logger {

    @AroundInvoke
    public Object handle(InvocationContext context){
        Object object = null;
        System.out.println("Before TRACE Logger");
        try {
            object = context.proceed();
            System.out.println("After TRACE Logger");
        } catch (Exception e) {
            System.out.println("TRACE : "+e.getMessage()+" logged..");
        }
        return object;
    }

}
