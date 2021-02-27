package com.nlxr.juc.basic;

/**
 * 单例模式 -- 双重校验锁
 */
class Singleton{
    //禁止指令重排
    private volatile static Singleton uniqueInstance;
    //uniqueInstance = new Singleton();这段代码其实分为三步执行：
    //1.为uniqueInstance分配内存空间
    //2.初始化uniqueInstance
    //3.将uniqueInstance指向分配的内存地址
    //由于JVM具有指令重排的特性，执行顺序有可能变成1->3->2。
    //指令重排在单线程环境下不会出现问题，但是在多线程环境下会导致一个线程获得还没有初始化的实例。
    //例如：线程t1执行了1和3，此时t2调用getUniqueInstance()后发发现uniqueInstance不为空，
    // 因此返回未完成初始化的uniqueInstance。使用volatile可以禁止JVM的指令重排,保证多线程环境下也能正常运行。

    //私有化构造器
    private Singleton (){
    }

    public static Singleton getUniqueInstance(){
        //先判断对象是否实例过，为实例过才进入加锁代码
        if (null == uniqueInstance){
            //类对象加锁
            synchronized (Singleton.class){
                if (null == uniqueInstance){
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}
