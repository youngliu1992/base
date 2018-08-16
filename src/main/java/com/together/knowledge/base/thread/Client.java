package com.together.knowledge.base.thread;

import java.util.concurrent.CountDownLatch;

public class Client {

    public static void main(String[] args){
        ThreadPoolExecutorFactory threadPoolExecutorFactory = new ThreadPoolExecutorFactory();
        CloseableExecutor closeableExecutor = threadPoolExecutorFactory.newExecutor("liuy");
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        closeableExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++) {
                    System.out.println(Thread.currentThread().getThreadGroup() + "-" + Thread.currentThread().getId() + "-" + Thread.currentThread().getName() + "-Hello World");
                    countDownLatch.countDown();
                }
            }
        });

        CloseableExecutor closeableExecutor2 = threadPoolExecutorFactory.newExecutor("liuy2");
        closeableExecutor2.execute(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++) {
                    System.out.println(Thread.currentThread().getThreadGroup() + "-" + Thread.currentThread().getId() + "-" + Thread.currentThread().getName() + "-Hello World");
                    countDownLatch.countDown();
                }
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束");

        closeableExecutor.shutdown();
    }

}
