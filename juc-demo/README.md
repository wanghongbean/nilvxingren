# 并发编程

## 1.多线程

#### 1.1 单生产者多消费者模式
> **why**

实际应用中生产者多数是查询db、获取、加载数据等消耗IO资源的任务。单线程处理大多时间需要等待IO，效率较低。

> **what**

主线程用于prodcer，另起多个consumer线程。

![核心类图](https://github.com/wanghongbean/nilvxingren/blob/master/img/SingleProducerMultiConsumer.png)

> **how**

1. 实现ProducerHandler和ConsumerHandler 处理生产数据的业务和消费数据的业务
2. 根据实际业务指定阻塞队列size、消费者线程数



