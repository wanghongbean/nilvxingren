package com.nlxr.juc.lock;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingQueueProducerConsumerDemo {
    public static void main(String[] args) {
        BlockingQueueResource resource = new BlockingQueueResource();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                resource.producer();
            },"producer-"+i).start();
        }
        System.out.println("=========");
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                resource.consumer();
            },"consumer-"+i).start();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(10000);
            resource.stop();
        } catch (InterruptedException e) {

        }

    }
}

class BlockingQueueResource{
    private BlockingQueue<Integer> queue = new SynchronousQueue();
//    private BlockingQueue<Integer> queue = new LinkedBlockingQueue(10);
    private AtomicInteger data = new AtomicInteger();
    private volatile boolean running = true;

    public void producer(){
        int i = data.incrementAndGet();
        try {
            while (running){
                queue.offer(i,2l,TimeUnit.SECONDS);
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(Thread.currentThread().getName()+" == producer data: " + i);
            }
        } catch (InterruptedException e) {

        }
    }

    public void consumer(){
        Integer poll = null;
        try {
            while (running){
                poll = queue.poll(2l,TimeUnit.SECONDS);
                if (null == poll){
                    System.out.println(Thread.currentThread().getName()+" consumer null 2s");
                    return;
                }
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(Thread.currentThread().getName()+" == consumer data: " + poll);
            }
        } catch (InterruptedException e) {

        }
    }

    public void stop(){
        this.running = false;
        System.out.println("ready stop...");
    }
}