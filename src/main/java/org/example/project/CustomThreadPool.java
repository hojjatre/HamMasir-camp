package org.example.project;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class CustomThreadPool {
    private final int numThreads;
    private final Thread[] threads;
    private final BlockingQueue<Runnable> taskQueue;
    private AtomicBoolean isShutdown;
    private final AtomicInteger completedTasks;
    public CustomThreadPool(int numThreads) {
        this.numThreads = numThreads;
        this.threads = new Thread[numThreads];
        this.taskQueue = new LinkedBlockingQueue<>();
        this.isShutdown = new AtomicBoolean(false);
        this.completedTasks = new AtomicInteger(0);

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new WorkerThread();
            threads[i].start();
        }
    }

    public void submit(Runnable task) {
        if (!isShutdown.get()) {
            try {
                taskQueue.put(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        if (!isShutdown.getAndSet(true)) {
            for (Thread thread : threads) {
                thread.interrupt();
            }
        }
    }

    public void awaitTermination() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (!isShutdown.get() || !taskQueue.isEmpty()) {
                Runnable task = null;
                try {
                    task = taskQueue.take();
                    task.run();
                    completedTasks.incrementAndGet();
                } catch (InterruptedException e) {
                    // Thread interrupted, but just continue the loop
                }
            }
        }
    }

    public synchronized boolean isTerminated() {
        return taskQueue.isEmpty();
    }
}

