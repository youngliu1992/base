package com.together.knowledge.base.serializer.api.hessianserializer;

import com.together.knowledge.base.serializer.api.Serializer;

public class Client {

    public static void main(String[] args){
        Serializer serializer = new HessianSerializer();
        byte[] wo = serializer.writeObject(100);
        int result = serializer.readObject(wo,Integer.class);
        System.out.println(result);
    }

}
