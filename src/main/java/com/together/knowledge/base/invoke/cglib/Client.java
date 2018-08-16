package com.together.knowledge.base.invoke.cglib;

//19966
public class Client {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for(int i=0;i < 1000000;i++) {
            UserServiceCglib cglib = new UserServiceCglib();
            UserServiceImpl bookFacedImpl = (UserServiceImpl) cglib.getInstance(new UserServiceImpl());
            bookFacedImpl.addUser();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
