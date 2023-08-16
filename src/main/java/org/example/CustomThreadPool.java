package org.example;

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
        isShutdown.set(true);
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable task = null;
                while (taskQueue.isEmpty() && !isShutdown.get()) {
                    try {
                        // Wait for tasks or shutdown signal
                        taskQueue.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Exception");
                    }
                }
                if (isShutdown.get() && taskQueue.isEmpty()) {
                    // Exit the thread if shutdown is requested and no tasks are left
                    break;
                }
                task = taskQueue.poll();
                if (task != null) {
                    try {
                        task.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    synchronized (CustomThreadPool.this) {
                        completedTasks.set(completedTasks.get()+1);
                        if (completedTasks.get() == numThreads) {
                            // Signal that all threads have completed
                            CustomThreadPool.this.notify();
                        }
                    }
                }
            }
        }
    }

    public synchronized boolean isTerminated() {
        return taskQueue.size() == 0;
    }
}

