package com.nlxr.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockDemo {
    public static void main(String[] args) {
//        deadLockDemo();
        deadSyncLockDemo();
    }

    private static void deadLockDemo() {
        ReentrantLock lockA = new ReentrantLock();
        ReentrantLock lockB = new ReentrantLock();
        new Thread(() -> {
            try {
                lockA.lock();
                System.out.println(Thread.currentThread().getName() + "lock A ...");
                TimeUnit.SECONDS.sleep(2);
                lockB.lock();
                System.out.println(Thread.currentThread().getName() + "lock B ...");
            } catch (InterruptedException e) {

            } finally {
                lockA.unlock();
                lockB.unlock();
            }
        }, "AAA").start();

        new Thread(() -> {
            try {
                lockB.lock();
                System.out.println(Thread.currentThread().getName() + "lock B ...");
                TimeUnit.SECONDS.sleep(2);
                lockA.lock();
                System.out.println(Thread.currentThread().getName() + "lock A ...");
            } catch (InterruptedException e) {

            } finally {
                lockA.unlock();
                lockB.unlock();
            }
        }, "BBB").start();
    }

    private static void deadSyncLockDemo() {
        String A = "AAA";
        String B = "BBB";
        new Thread(() -> {
            try {
                synchronized (A) {
                    System.out.println(Thread.currentThread().getName() + " lock A ...");
                    TimeUnit.SECONDS.sleep(2);
                    synchronized (B) {
                        System.out.println(Thread.currentThread().getName() + " lock B ...");
                    }
                }
            } catch (InterruptedException e) {

            }
        }, "AAA").start();

        new Thread(() -> {
            try {
                synchronized (B) {
                    System.out.println(Thread.currentThread().getName() + " lock B ...");
                    TimeUnit.SECONDS.sleep(2);
                    synchronized (A) {
                        System.out.println(Thread.currentThread().getName() + " lock A ...");
                    }
                }
            } catch (InterruptedException e) {

            }
        }, "BBB").start();
    }
}
