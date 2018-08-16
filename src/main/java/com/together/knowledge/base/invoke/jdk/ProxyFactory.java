package com.together.knowledge.base.invoke.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    private static final ProxyFactory proxyFactory = new ProxyFactory();

    public static ProxyFactory getInstance(){
        return proxyFactory;
    }

    public <T> T getInvoker(InvocationHandler invocationHandler, Class<T> obj){
        Object proxyObject = Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(),obj.getInterfaces(),
                invocationHandler);
        T a = (T) proxyObject;
        return (T)proxyObject;
    }

}
