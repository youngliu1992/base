package com.together.knowledge.base.invoke.jdk;

import java.lang.reflect.InvocationHandler;

public class Client {

    public static void main(String[] args){
        long start = System.currentTimeMillis();
        for(int i=0;i < 1000000;i++) {
            InvocationHandler invocationHandler = new SubjectInvoction(new RealSubject());
            Subject proxySubject = ProxyFactory.getInstance().getInvoker(invocationHandler, RealSubject.class);
            proxySubject.print();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
