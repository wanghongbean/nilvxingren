#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import multiprocessing as mp

#定义任务
def job(x):
    return x*x

#多线程demo
def multicore():
    mp.freeze_support()#windows 平台加上这句，避免 RuntimeError
    cpu_num = mp.cpu_count()
    print('cpu 核心数 : ',cpu_num)
    #pool = mp.Pool() 核数量默认cpu的核数
    pool = mp.Pool(processes=cpu_num)#指定cpu核数量为 cpu核心数
    res = pool.map(job,range(10))#传入job函数和需要迭代的运算值
    print(res)

    #apply_async() 异步执行
    res = pool.apply_async(job,(2,))#只能传入一个可迭代形式的值
    print(res.get())

    #res = pool.apply_async(job,(2,3,4,))#TypeError: job() takes 1 positional argument but 3 were given
    #print(res.get())
    
    #apply_async()正确用法
    #迭代器 i=0时apply一次，i=1时apply一次 。。。
    multi_res = [pool.apply_async(job,(i,)) for i in range(10)]
    
    pool.close()
    pool.join()

    print([res.get() for res in multi_res])

if __name__ == "__main__":
    multicore()    


# apply_async 用法 https://jingsam.github.io/2015/12/31/multiprocessing.html
#pool.apply_async 采用异步方式调用 task，pool.apply 则是同步方式调用。同步方式意味着下一个
#  task 需要等待上一个 task 完成后才能开始运行，这显然不是我们想要的功能，所以采用异步方式连续
# 地提交任务。在上面的语句中，我们提交了 4 个任务，假设我的 CPU 是 4 核，那么我的每个核运行一个
# 任务。如果我提交多于 4 个任务，那么每个核就需要同时运行 2 个以上的任务，这回带来任务切换成本，
# 降低了效率。所以我们设置的并行任务数最好等于 CPU 核心数， CPU 核可以通过cpu_count()

#在这里不免有人要疑问，为什么不直接在 for 循环中直接 result.get()呢？
# 这是因为pool.apply_async之后的语句都是阻塞执行的，调用 result.get() 会等待上一个任务执行完
# 之后才会分配下一个任务。事实上，获取返回值的过程最好放在进程池回收之后进行，避免阻塞后面的语句。