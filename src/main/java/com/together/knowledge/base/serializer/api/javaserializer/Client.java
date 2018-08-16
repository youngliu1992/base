package com.together.knowledge.base.serializer.api.javaserializer;

import com.together.knowledge.base.serializer.api.Serializer;

public class Client {

    public static void main(String[] args){
        Serializer serializer = new JavaSerializer();
        byte[] wr = serializer.writeObject(100);
        Integer result = serializer.readObject(wr,Integer.class);
        System.out.println(result);
    }

}
