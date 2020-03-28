package com.nlxr.juc.thread;

import com.nlxr.juc.handler.ConsumerHandler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * The type Consumer thread.
 *
 * @param <T> the type parameter
 */
public class ConsumerThread<T> extends Thread {

    /**
     * 阻塞队列-待消费的队列
     */
    private final LinkedBlockingQueue<T> blockingQueue;
    /**
     * 业务处理
     */
    private final ConsumerHandler<T> handler;
    /**
     * 闭锁
     */
    private final CountDownLatch latch;
    /**
     * 毒丸
     */
    private final T poisonPill;
    /**
     * 线程状态
     */
    private volatile boolean stop = false;

    public ConsumerThread(String name, LinkedBlockingQueue<T> blockingQueue, ConsumerHandler<T> handler, CountDownLatch latch, T poisonPill) {
        super(name);
        this.blockingQueue = blockingQueue;
        this.handler = handler;
        this.latch = latch;
        this.poisonPill = poisonPill;
    }

    /**
     * 关闭线程标记
     */
    private void shutdown() {
        this.stop = true;
    }

    @Override
    public void run() {
        System.out.println(String.format("========= consumer thread [%s] STARTED.", Thread.currentThread().getName()));
        while (!stop) {
            try {
                //非阻塞取任务
                T t = blockingQueue.poll();
                if (null == t) {
                    System.out.println(String.format("========= consumer thread [%s] -no task,sleep 1s.", getName()));
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                } else if (poisonPill == t) {
                    System.out.println(String.format("========= consumer thread [%s] -all task done,prepare to shutdown.", getName()));
                    blockingQueue.put(poisonPill);//put back to kill others
                    this.shutdown();
                } else {
                    //handler task
                    handler.consumer(t);
                }
            } catch (Throwable e) {
                System.out.println(String.format("========= consumer thread [%s] -error", getName()));
                e.printStackTrace();
            }
        }
        latch.countDown();//-1,当前线程执行完毕
        System.out.println(String.format("========= consumer thread name[%s] shutdown.", Thread.currentThread().getName()));


    }
}
