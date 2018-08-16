package com.together.knowledge.base.thread;

public interface ExecutorFactory {
    CloseableExecutor newExecutor(String name);
}
