package com.tdt.dict;

import javax.xml.catalog.CatalogFeatures;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreading {
    public static MultiThreading instance;
    private final static int fixedPoolSize = 8;
    private ExecutorService executor;

    private MultiThreading() {
        executor = Executors.newFixedThreadPool(fixedPoolSize);
    }

    public static MultiThreading getInstance() {
        if (instance == null) {
            instance = new MultiThreading();
        }
        return instance;
    }

    public void submitRunnableTask(Runnable task) {
        executor.submit(task);
    }

    public Future submitCallableTask(Callable task) {
        Future future = executor.submit(task);
        return future;
    }

    public void shutdown() {
        executor.shutdown();
    }

    public boolean isTerminated() {
        return executor.isTerminated();
    }
}
