package com.together.knowledge.base.invoke.designinvok;

import java.lang.reflect.InvocationTargetException;

public class ProxyFactory extends DefaultInvoction{

    public void print(Integer a){
        System.out.println("Hello World");
    }


    @Override
    public Object doBefore() {
        System.out.println("before");
        return null;
    }

    @Override
    public Object doAfter() {
        System.out.println("after");
        return null;
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        long start = System.currentTimeMillis();
        ProxyFactory proxyFactory = new ProxyFactory();
        for(int i=0;i < 1000000;i++) {
            proxyFactory.invoke("print", proxyFactory, new Integer(1));
            /*proxyFactory.doBefore();
            proxyFactory.print(1);
            proxyFactory.doAfter();*/
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
