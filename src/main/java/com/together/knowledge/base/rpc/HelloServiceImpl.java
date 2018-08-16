package com.together.knowledge.base.rpc;

public class HelloServiceImpl implements HelloService{
    public String hello(String name) {
        return "Hello " + name;
    }
}
