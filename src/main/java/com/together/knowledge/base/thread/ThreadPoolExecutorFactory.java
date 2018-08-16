package com.together.knowledge.base.thread;

import java.lang.reflect.Constructor;
import java.util.concurrent.*;

public class ThreadPoolExecutorFactory implements ExecutorFactory {

    protected ThreadFactory threadFactory(String name) {
       return new NamedThreadFactory(name);
    }

    private RejectedExecutionHandler createRejectedPolicy( String name,
                                                           RejectedExecutionHandler defaultHandler) {
        RejectedExecutionHandler handler = null;
        String handlerClass = null;
        if (handlerClass != null) {
            try {
                Class<?> cls = Class.forName(handlerClass);
                try {
                    Constructor<?> constructor = cls.getConstructor(String.class, String.class);
                    handler = (RejectedExecutionHandler) constructor.newInstance(name, "jupiter");
                } catch (NoSuchMethodException e) {
                    handler = (RejectedExecutionHandler) cls.newInstance();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return handler == null ? defaultHandler : handler;
    }

    public CloseableExecutor newExecutor(String name) {

        final ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                1,
                120L,
                TimeUnit.SECONDS,
                workQueue(),
                threadFactory(name),
                createRejectedPolicy( name, new RejectedTaskPolicyWithReport(name, "jupiter")));

        return new CloseableExecutor() {
            public void execute(Runnable r) {
                executor.execute(r);
            }
            public void shutdown() {
                executor.shutdownNow();
            }
        };
    }


    private BlockingQueue<Runnable> workQueue() {
        return new LinkedBlockingQueue<Runnable>(10);
    }


}
