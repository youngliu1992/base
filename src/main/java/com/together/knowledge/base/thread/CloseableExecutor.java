package com.together.knowledge.base.thread;

public interface CloseableExecutor {
    void execute(Runnable r);

    void shutdown();
}
