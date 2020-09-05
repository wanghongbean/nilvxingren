package com.nlxr.gc;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class JavaGCDemo {
    //useCMS 收集器
    //-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC
    //-XX:+UseCMSCompactAtFullCollection 减少内存碎片?
    public static void main(String[] args) {
        System.out.println("gc test ... ");
        String str ="wanghongbin";
        while (true){
            str += str + new Random().nextInt(17777777)+"sdsdfa"+new Random(1000000).nextInt(2212222);
            str.intern();
        }
    }
}
