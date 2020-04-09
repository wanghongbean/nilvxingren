package com.nlxr.juc.concurrent.handler;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * The interface Producer handler.
 *
 * @param <T> the type parameter
 */
public interface ProducerHandler<T> {

    /**
     * Producer all.生产数据放到阻塞队列，直到结束
     *
     * @param blockingQueue the blocking queue
     */
    void producerAll(final LinkedBlockingQueue<T> blockingQueue);
}
