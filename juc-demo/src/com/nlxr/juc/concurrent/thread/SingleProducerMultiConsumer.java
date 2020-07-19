package com.nlxr.juc.concurrent.thread;

import com.nlxr.juc.concurrent.handler.ConsumerHandler;
import com.nlxr.juc.concurrent.handler.ProducerHandler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The type Single producer multi consumer.
 * 当前线程作为单一生产线程
 * 指定多个消费线程
 * @param <T> the type parameter
 */
public class SingleProducerMultiConsumer<T> {
    /**
     * 生产者处理程序
     */
    private final ProducerHandler<T> producerHandler;
    /**
     * 消费者处理程序
     */
    private final ConsumerHandler<T> consumerHandler;
    /**
     * 线程名称前缀
     */
    private final String threadNamePrefix;
    /**
     * 毒丸
     */
    private final T poisonPill;
    /**
     * 消费者线程数量
     */
    private final int consumerCount;
    /**
     * 闭锁
     */
    private final CountDownLatch latch;
    /**
     * 阻塞队列-任务队列
     */
    private final LinkedBlockingQueue<T> blockingQueue;

    /**
     * Instantiates a new Single producer multi consumer.
     *
     * @param queueCapacity    the queue capacity
     * @param producerHandler  the producer handler
     * @param consumerHandler  the consumer handler
     * @param threadNamePrefix the thread name prefix
     * @param poisonPill       the poison pill
     * @param consumerCount    the consumer count
     * @param latch            the latch
     */
    public SingleProducerMultiConsumer(int queueCapacity, ProducerHandler<T> producerHandler, ConsumerHandler<T> consumerHandler, String threadNamePrefix, T poisonPill, int consumerCount, CountDownLatch latch) {
        this.producerHandler = producerHandler;
        this.consumerHandler = consumerHandler;
        this.threadNamePrefix = threadNamePrefix;
        this.poisonPill = poisonPill;
        this.consumerCount = consumerCount;
        this.latch = latch;
        this.blockingQueue = new LinkedBlockingQueue<>(queueCapacity);
    }

    /**
     * Run utill done.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void runUtillDone() throws InterruptedException {
        //启动多个消费线程
        for (int i=0;i<consumerCount;i++){
            new ConsumerThread<>(threadNamePrefix+i,blockingQueue,consumerHandler,latch,poisonPill).start();
        }

        try {
            //生产线程生产任务数据
            producerHandler.producerAll(blockingQueue);
        }catch (Exception e){
            System.out.println("producer task error.");
            e.printStackTrace();
        }finally {
            //生产数据完成，毒丸入队列
            blockingQueue.put(poisonPill);
            //主线程等待消所有费线程结束
            latch.await();
        }
    }
}
