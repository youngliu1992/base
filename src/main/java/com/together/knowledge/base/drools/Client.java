package com.together.knowledge.base.drools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args) throws Exception {
        final RuleEngine ruleEngine = new RuleEngineImpl();
        ruleEngine.initEngine();
        final CountDownLatch countDownLatch = new CountDownLatch(200);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for(int i=0;i < 200;i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    PointDomain pointDomain = new PointDomain();
                    pointDomain.setUserName("hello world");
                    pointDomain.setBackMondy(100d);
                    pointDomain.setBuyMoney(500d);
                    pointDomain.setBackNums(1);
                    pointDomain.setBuyNums(5);
                    pointDomain.setBillThisMonth(5);
                    pointDomain.setBirthDay(true);
                    pointDomain.setPoint(0l);
                    try {
                        ruleEngine.executeRuleEngine(pointDomain);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() +"执行完毕BillThisMonth：" + pointDomain.getBillThisMonth());
                    System.out.println(Thread.currentThread().getName() +"执行完毕BuyMoney：" + pointDomain.getBuyMoney());
                    try {
                        Thread.sleep(1000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() +"执行完毕BuyNums：" + pointDomain.getBuyNums());
                    System.out.println(Thread.currentThread().getName() +"执行完毕规则引擎决定发送积分：" + pointDomain.getPoint());
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println("执行完毕");
        executorService.shutdown();
    }
}
