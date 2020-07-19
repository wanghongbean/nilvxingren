package com.nlxr.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * The type Common product consumer demo.
 * 普通生产者消费者：自己实现消费者生产者互相唤醒
 */
public class CommonProductConsumerDemo {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        CommonResorceData commonResorceData = new CommonResorceData();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                commonResorceData.producer();
            },"producer-"+i).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                commonResorceData.consumer();
            },"consumer-"+i).start();
        }
    }
}


/**
 * The type Common resorce data.
 */
class CommonResorceData {
    private Lock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();

    private int data;

    /**
     * Producer.
     */
    public void producer(){
        try {
            reentrantLock.lock();
            while (data != 0){
                condition.await(); ;
            }
            data ++ ;
            System.out.println(Thread.currentThread().getName()+" producer --- data: "+ data);
            condition.signalAll();
        } catch (InterruptedException e) {

        }finally {
            reentrantLock.unlock();
        }
    }

    /**
     * Consumer.
     */
    public void consumer(){
        try {
            reentrantLock.lock();
            while (data == 0){
                condition.await();
            }
            data --;
            System.out.println(Thread.currentThread().getName()+" consumer --- data: "+data);
            condition.signalAll();
        } catch (InterruptedException e) {

        }finally {
            reentrantLock.unlock();
        }
    }
}
