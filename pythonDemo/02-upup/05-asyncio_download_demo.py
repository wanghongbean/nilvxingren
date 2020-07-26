#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import requests
import os
import re
import random
from bs4 import BeautifulSoup
import time
import asyncio
import aiohttp
import aiofiles

DIR = '/mnt/d/guojiadili/asyncio'
FILE = DIR + '/%s'

#下载网页job
async def get_html_job(session,url):
    print('get url: ',url)
    async with session.get(url) as rep:
        if rep.status in [200,201]:
            return await rep.text()

#解析page页面内容job
def parse_page_job(html):
    soup =  BeautifulSoup(html,features='lxml')
    h4  =  soup.find_all('h4',{"class":"photo_list_title"})
    detail_urls = [  con.find('a')['href'] for con in h4]
    return detail_urls

#解析图片详情内容job
def parse_detail_job(detail):
    if len(detail)>0:
        isoup =  BeautifulSoup(detail,features='lxml')
        return   isoup.find('div',{"id":"detailPic"}).find('img')['src']

#下载图片到本地job
async def download_img(session,url):
    #print('download url:',url)
    pic = url.split('/')[-1]#按照 / 分隔 取最后一个作为pic name
    async with session.get(url) as rep:
        if rep.status in [200,201]:
            data = await rep.read()
            with open(FILE % pic,'wb') as d:
                d.write(data)

#aiofiles 下载图片
async def download_img_aio(session,url):
    #print('download url:',url)
    pic = url.split('/')[-1]#按照 / 分隔 取最后一个作为pic name
    async with session.get(url) as rep:
        if rep.status in [200,201]:
            data = await rep.read()
            async with aiofiles.open(FILE % pic,'wb') as d:
                await d.write(data)
                await d.close()




#单线程异步模式
#可以理解线程执行的任务阻塞时执行其他的任务
async def asy_job(session,url):
    page_html = await get_html_job(session,url)
    detail_urls =  parse_page_job(page_html)
    print('detail urls:',detail_urls)
    for du in detail_urls:
        detail_html = await get_html_job(session,du)
        img_url =  parse_detail_job(detail_html)
        if len(img_url)>0:
            #await download_img(session,img_url)
            await download_img_aio(session,img_url)
        else:
            print('img url is null')

async def async_page_job(loop,urls):
    print('total urls:',urls)
    async with aiohttp.ClientSession() as session:
        #tasks = []
        #for u in urls:
        #    tasks.append(loop.create_task(asy_job(session,u)))
        tasks = [loop.create_task(asy_job(session,u)) for u in urls]
        await asyncio.wait(tasks)

def async_download():
    t = time.time()
    url = "http://www.ngchina.com.cn/index.php?m=content&c=index&a=lists&catid=669&page=%s"
    urls = [url % t for t in range(1,3)]
    os.makedirs(DIR,exist_ok=True)
    loop = asyncio.get_event_loop()
    loop.run_until_complete(async_page_job(loop,urls))
    loop.close()
    print('aiohttp download time: ',time.time()-t)

if __name__ == "__main__":
    async_download()