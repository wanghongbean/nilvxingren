#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import time
import asyncio

def job(t):
    print('start job',t)
    time.sleep(t)
    print('job',t,'takes',t,'s')

#同步模式
def sub_job():
    t1 = time.time()
    [job(t) for t in range(1,3)]# range(1,3) 包含1,2
    print('no async total time: ',time.time()-t1)

#单线程异步模式
#可以理解线程执行的任务阻塞时执行其他的任务
async def asy_job(t):
    print('start job',t)
    await asyncio.sleep(t)
    print('job',t,'takes',t,'s')

async def async_sub_job(loop):
    tasks = [
        loop.create_task(asy_job(t)) for t in range(1,3)
    ]
    await asyncio.wait(tasks)

def test_async():
    t = time.time()
    loop = asyncio.get_event_loop()
    loop.run_until_complete(async_sub_job(loop))
    loop.close()
    print('async total time: ',time.time()-t)

if __name__ == "__main__":
    sub_job()  
    test_async()     