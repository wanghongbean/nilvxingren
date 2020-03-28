package com.nlxr.juc.handler;

/**
 * 消费者处理程序
 *
 * @author labu
 * @date 2020/03/28
 */
public interface ConsumerHandler<T> {
    /**
     * 消费者
     *
     * @param t t
     */
    void consumer(T t);
}
