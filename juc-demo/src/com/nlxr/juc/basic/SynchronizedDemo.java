package com.nlxr.juc.basic;

public class SynchronizedDemo {

    //    public void method(){
//        synchronized (this){
//            System.out.println("synchronized 代码块");
//        }
//    }
    public synchronized void method() {
        System.out.println("synchronized 方法");
    }
}