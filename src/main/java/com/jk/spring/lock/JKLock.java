package com.jk.spring.lock;

import java.util.concurrent.locks.ReentrantLock;

public class JKLock {
    ReentrantLock reentrantLock = new ReentrantLock();

    public synchronized void checkSync(String threadId) {
        System.out.println("JK Thread in (synchronized) : " + threadId);
    }

    public void checkReentrantLock(String threadId) {
        reentrantLock.lock();
//        reentrantLock.tryLock();
//        reentrantLock.lockInterruptibly();
        System.out.println("JK Thread in (Reentrant) : " + threadId);
        reentrantLock.unlock();
    }

    public void noneLock(String threadId) {
        System.out.println("JK Thread in (None lock) : " + threadId);
    }
}
