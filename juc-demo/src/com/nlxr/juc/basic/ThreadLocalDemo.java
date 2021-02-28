package com.nlxr.juc.basic;

import org.junit.Test;

public class ThreadLocalDemo {

    @Test
    public void test_threadLocal(){
        Thread thread = Thread.currentThread();
        ThreadLocal tl= new ThreadLocal();

        tl.set("AAA");
        System.out.println(tl.get());
        new Thread(()-> System.out.println(tl.get())).start();

    }
}
