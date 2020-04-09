package com.nlxr.juc;

import com.nlxr.juc.concurrent.bo.StudentBo;
import com.nlxr.juc.concurrent.handler.ConsumerHandler;
import com.nlxr.juc.concurrent.handler.ProducerHandler;
import com.nlxr.juc.concurrent.thread.SingleProducerMultiConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Main {
    /*
     * 模拟任务：计算每个学生总分
     */
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Random random = new Random();
        //单生产者多消费者应用
        ProducerHandler<StudentBo> producerHandler = (blockingQueue) -> {
            List<StudentBo> studentBos = new ArrayList<>();
            while (true) {
                //模拟 查询db 加工数据
                StudentBo bo = new StudentBo(random.nextInt(100), random.nextInt(100), "student" + random.nextInt(10000));
                studentBos.add(bo);
                try {
                    blockingQueue.put(bo);
                } catch (InterruptedException e) {
                    //ignore
                }
                //模拟生产数据结束
                if (studentBos.size() > 10000) {
                    break;
                }
            }
        };

        ConsumerHandler<StudentBo> consumerHandler = (studentBo) -> {
            System.out.println(String.format("学生名称：%s  课程A分数：%s  课程B分数：%s 总分：%s ",
                    studentBo.hashCode(),
                    studentBo.getAScore(),
                    studentBo.getBScore(),
                    studentBo.getAScore() + studentBo.getBScore()));
        };
        new SingleProducerMultiConsumer<>(500, producerHandler, consumerHandler,
                "student-score_", new StudentBo(), 10, new CountDownLatch(10))
                .runUtillDone();

        long end = System.currentTimeMillis();
        System.out.println("程序运行时间：" + (end - start) / 1000 + " s");

    }

    //单线程处理方式
    public void taskHandler() {
        //load data

        //转换、校验处理

        //写数据、业务处理
    }
}
