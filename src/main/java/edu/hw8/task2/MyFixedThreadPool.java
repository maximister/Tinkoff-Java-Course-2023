package edu.hw8.task2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyFixedThreadPool implements ThreadPool {

    private final static String THREADS_AMOUNT_EXCEPTION = "Amount of threads must b bigger than 0";
    private final static String POOL_IS_NOT_STARTED = "Thread pool was not started";
    private final AtomicBoolean canBeExecuted = new AtomicBoolean(false);
    private final BlockingQueue<Runnable> blockingQueue;
    private final MyThreadWorker[] threads;

    private MyFixedThreadPool(int threadAmount) {
        if (threadAmount <= 0) {
            throw new IllegalArgumentException(THREADS_AMOUNT_EXCEPTION);
        }
        this.threads = new MyThreadWorker[threadAmount];
        blockingQueue = new LinkedBlockingQueue<>();
    }

    public static MyFixedThreadPool newMyFixedThreadPool(int threads) {
        return new MyFixedThreadPool(threads);
    }

    @Override
    public void start() {
        canBeExecuted.set(true);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThreadWorker();
            threads[i].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (!canBeExecuted.get()) {
            throw new IllegalStateException(POOL_IS_NOT_STARTED);
        }
        try {
            blockingQueue.put(runnable);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void awaitTermination() {
        for (MyThreadWorker worker : threads) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void close() throws Exception {
        canBeExecuted.set(false);
    }

    private final class MyThreadWorker extends Thread {
        @Override
        public void run() {
            while (canBeExecuted.get() || !blockingQueue.isEmpty()) {
                Runnable runnable;
                while ((runnable = blockingQueue.poll()) != null) {
                    runnable.run();
                }
            }
        }
    }
}
