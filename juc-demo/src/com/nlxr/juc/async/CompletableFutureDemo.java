package com.nlxr.juc.async;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFutureDemo
 *
 * CompletableFuture可以指定异步处理流程：
 *
 * thenAccept()处理正常结果；
 * exceptional()处理异常结果；
 * thenApplyAsync()用于串行化另一个CompletableFuture；
 * anyOf()和allOf()用于并行化多个CompletableFuture。
 *
 * @author labu
 * @date 2020/09/10
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws InterruptedException {
//        simpleTest();
//        serialTaskDemo();
        parallelTaskDemo();

        //主线程不要立即结束，否则CompletableFuture默认使用的线程池会立刻关闭
        TimeUnit.SECONDS.sleep(10);

    }

    /**
     * 并行任务演示
     */
    private static void parallelTaskDemo() {
        CompletableFuture<Integer> first = CompletableFuture.supplyAsync(() -> randomInt(2000));
        CompletableFuture<Integer> second = CompletableFuture.supplyAsync(() -> randomInt(4000));
        //anyOf 表示任意一个执行完返回结果 就继续 allOf 表示所以执行完在继续
        CompletableFuture<Object> cfAny = CompletableFuture.anyOf(first, second);
        CompletableFuture<Integer> first1 = cfAny.thenApplyAsync(i -> paramDouble((int)i, 5000));
        CompletableFuture<Integer> second1 = cfAny.thenApplyAsync(i -> paramDouble((int)i, 1000));
        CompletableFuture<Object> cfpAny = CompletableFuture.anyOf(first1, second1);
        cfpAny.thenAccept(res-> System.out.println("result :"+res));

        cfpAny.exceptionally(e-> {System.out.println("error ... ");return null;});
    }

    /**
     * 串行任务演示
     */
    private static void serialTaskDemo() {
        //first task
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(CompletableFutureDemo::randomInt);
        //second task
        CompletableFuture<Integer> cfd = cf.thenApplyAsync(i -> paramDouble(i));
        //handle result
        cfd.thenAccept(res-> System.out.println("double result:"+res));

        cfd.exceptionally(e->{
//            e.printStackTrace();
            System.out.println("exception ... "); //cfd 可以捕获到cf task中的异常
            return null;
        });
    }

    private static void simpleTest() throws InterruptedException {
        //创建异步执行任务
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(CompletableFutureDemo::randomInt);
        //success
        cf.thenAccept(i -> System.out.println("next int :" + i));
        //发生异常
        cf.exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }

    static Integer randomInt(Integer sleep){
        try {
            TimeUnit.MILLISECONDS.sleep(sleep);
        } catch (InterruptedException e) {

        }
        System.out.println("randomInt taskId:"+sleep);
        Random random = new Random();
        return random.nextInt(100);
    }

    static Integer paramDouble(int param,Integer taskId){
        try {
            TimeUnit.MILLISECONDS.sleep(taskId);
        } catch (InterruptedException e) {

        }
        System.out.println("taskId:"+taskId+" param double :"+param);
        return param * param;
    }

    static Integer randomInt() {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {

        }
        Random random = new Random();
        int i = random.nextInt(100);
        if (i < 50) {
            throw new RuntimeException("wo wo error");
        }
        return i;
    }

    static Integer paramDouble(int param){
        System.out.println("param double :"+param);
        return param * param;
    }
}
