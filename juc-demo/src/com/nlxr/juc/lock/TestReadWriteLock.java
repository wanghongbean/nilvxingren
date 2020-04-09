package com.nlxr.juc.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The type Test read write lock.
 */
public class TestReadWriteLock {
}

/**
 * The type My source.
 */
class MySource{
    private Map<String,String> map = new HashMap<>();
    private ReentrantReadWriteLock  reentrantReadWriteLock = new ReentrantReadWriteLock();

    /**
     * Get source. test read lock
     *
     * @param key the key
     * @return the string
     */
    public String get(String key){
        reentrantReadWriteLock.readLock().lock();
        try {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                //ignore
            }
            System.out.println(Thread.currentThread().getName()+" read == key: "+key);
            return map.get(key);
        }finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public void set(String key,String value){
        reentrantReadWriteLock.writeLock().lock();
        try {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                //ignore
            }
            System.out.println(Thread.currentThread().getName()+" write == key: "+key);
            map.put(key,value);
        }finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }


    public static void main(String[] args) {
        MySource mySource = new MySource();

        for (int i = 0; i < 10; i++) {
            final String tmp = i+"";
            new Thread(()->{
                mySource.set(tmp,tmp);
            },"write-"+i).start();
        }

        for (int i = 0; i < 10; i++) {
            final String tmp = i+"";
            new Thread(()->{
                mySource.get(tmp);
            },"read-"+i).start();
        }
    }

}
