package com.nlxr.juc.jmm;


/**
 * 单例演示
 *
 * @author labu
 * @date 2020/04/08
 */
public class SingletonDemo {

    /* DCL(双端检锁)机制不一定线程安全，因为指令重排的存在，加入volatile可以禁止指令重排
     * instance = new SingletonDemo() 可以分为以下步骤(伪代码)
     * memory = allocate();//1.分配对象内存空间
     * instance(memory);   //2.初始化对象
     * instance = memory;  //3.设置instance指向刚分配的内存地址，此时instance != null
     * 2和3不存在数据依赖关系，而且重排前和重排后程序执行结果在单线程中并没有改变，因此这种重排是允许的
     * 如：
     * 1 -> 3 （instance!=null,单对象未完成初始化）-> 2
     * 这种场景下，当一条线程访问instance不为null，但是未完成初始化，也就造成了线程安全问题
     */
    private static volatile SingletonDemo instance = null;

    private SingletonDemo() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " -- Singleton init....");
    }

    /**
     * 获得实例
     * double check locking 模式
     * @return {@link SingletonDemo}
     */
    public static SingletonDemo getInstance() {
        if (null == instance) {
            synchronized (SingletonDemo.class) {
                if (null == instance){
                    instance = new SingletonDemo();
                }
            }
        }

        return instance;
    }




    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                SingletonDemo instance = SingletonDemo.getInstance();
            }, i + "").start();
        }
    }
}
