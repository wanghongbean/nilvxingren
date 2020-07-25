#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import requests 
import time
import aiohttp
import asyncio

URL = 'https://www.baidu.com/'
#URL = 'https://morvanzhou.github.io/'

#普通模式
def normal():
    t = time.time()
    #[print(requests.get(URL).url) for i in range(2)]错误的写法
    #print(res)
    for i in range(2):
        url = requests.get(URL).url
        print(url)
    print('normal total time:',time.time()-t)

#aiohttp模式
async def job(session):
    rep = await session.get(URL)#等待并切换
    return str(rep.url)

async def sub_job(loop):
    async with aiohttp.ClientSession() as session: #官网推荐建立session的形式
        tasks = [loop.create_task(job(session)) for i in range(2)]
        finished,unfinished = await asyncio.wait(tasks)#finished 完成的，unfinished 未完成的
        all_results = [r.result() for r in finished] # 获取所有的结果
        print(all_results)

def test_aiohttp():
    t = time.time()
    loop = asyncio.get_event_loop()
    loop.run_until_complete(sub_job(loop))
    loop.close()
    print('aiohttp total time:',time.time()-t)


if __name__ == "__main__":
    normal()   
    test_aiohttp() 


#todo 待掌握语法 
# 1.res = [dosomesthing for i in ()] 
# 2.loop asyncio.get_event_loop()