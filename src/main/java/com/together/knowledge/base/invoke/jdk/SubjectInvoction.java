package com.together.knowledge.base.invoke.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SubjectInvoction implements InvocationHandler {

    private Object subject;

    public SubjectInvoction(Object subject){
        this.subject = subject;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理开始");
        method.invoke(subject,args);
        System.out.println("动态代理结束");
        return null;
    }

    public Object getSubject() {
        return subject;
    }

    public void setSubject(Object subject) {
        this.subject = subject;
    }
}
