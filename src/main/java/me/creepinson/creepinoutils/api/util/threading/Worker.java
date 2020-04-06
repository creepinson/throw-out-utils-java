package me.creepinson.creepinoutils.api.util.threading;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Worker extends Thread {
    private final AtomicBoolean shouldWait = new AtomicBoolean();

    protected abstract boolean processingIsComplete();

    protected abstract void process();

    protected abstract void cleanUpResources();

    public abstract Object getLock();

    public void disable() {
        shouldWait.set(false);
    }

    public void enable() {
        shouldWait.set(true);
    }

    @Override
    public void run() {
        try {
            while (!processingIsComplete()) {
                while (!shouldWait.get()) {
                    synchronized (getLock()) {
                        getLock().wait();
                    }
                }
            }
            process();
        } catch (InterruptedException e) {
            System.out.println("Worker thread stopped");
        } finally {
            cleanUpResources();
        }
    }
}