package com.together.knowledge.base.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
    private final AtomicInteger id = new AtomicInteger();
    private final String name;
    private final boolean daemon;
    private final int priority;
    private final ThreadGroup group;

    public NamedThreadFactory(String name) {
        this(name, false, Thread.NORM_PRIORITY);
    }

    public NamedThreadFactory(String name, boolean daemon) {
        this(name, daemon, Thread.NORM_PRIORITY);
    }

    public NamedThreadFactory(String name, int priority) {
        this(name, false, priority);
    }

    public NamedThreadFactory(String name, boolean daemon, int priority) {
        this.name = name + "#";
        this.daemon = daemon;
        this.priority = priority;
        SecurityManager s = System.getSecurityManager();
        group = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    public Thread newThread(Runnable r) {

        String name2 = name + id.getAndIncrement();

        Runnable r2 = wrapRunnable(r);

        Thread t = wrapThread(group, r2, name2);

        try {
            if (t.isDaemon() != daemon) {
                t.setDaemon(daemon);
            }

            if (t.getPriority() != priority) {
                t.setPriority(priority);
            }
        } catch (Exception ignored) { /* doesn't matter even if failed to set. */ }

        return t;
    }

    public ThreadGroup getThreadGroup() {
        return group;
    }

    protected Runnable wrapRunnable(Runnable r) {
        return r; // InternalThreadLocalRunnable.wrap(r)
    }

    protected Thread wrapThread(ThreadGroup group, Runnable r, String name) {
        return new InternalThread(group, r, name);
    }
}
