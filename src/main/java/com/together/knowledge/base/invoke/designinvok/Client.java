package com.together.knowledge.base.invoke.designinvok;

import java.lang.reflect.InvocationTargetException;

public class Client extends DefaultInvoction {
    public void print(Integer a,String b){
        System.out.println("Hello World"+b);
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
        Client client = new Client();
        for(int i=0;i < 1000000;i++) {
            client.invoke("print", client, 1,"liuy");
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
