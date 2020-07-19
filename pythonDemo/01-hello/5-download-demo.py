#!/usr/bin/env python3
# -*- coding: utf-8 -
import requests
import os
import re
import random
from bs4 import BeautifulSoup
import datetime

BASE_URL ="http://image.ngchina.com.cn"

#爬取国家地理图片
def download_demo():
    url = "/urlpic/108997/2020/07141717041089975895.jpeg"
    os.makedirs('./img/gjdl/',exist_ok=True)
    r = requests.get(BASE_URL+url,stream=True)
    with open('./img/gjdl/07141717041089975895.jpeg','wb') as f:
        for chunk in r.iter_content(chunk_size=32):
            f.write(chunk)

#下载 国家地理2019摄影大赛 地方类 分页 随机
top_url ="http://www.ngchina.com.cn/index.php"
def download_random():
    start = datetime.datetime.now()
    dir = '/mnt/d/guojiadili'
    os.makedirs(dir,exist_ok=True)
    url = "http://www.ngchina.com.cn/index.php?m=content&c=index&a=lists&catid=667&page=%s"
    real_url = "http://www.ngchina.com.cn/index.php?m=content&c=index&a=show&catid=667&id=289025"
    #http://image.ngchina.com.cn/userpic/108997/2020/07141717481089974672.jpeg
    #page_param = {"m":"content","c":"index","a":"list","catid":667,"page":1}
    #real_param = {"m":"content","c":"index","a":"show","catid":667,"id":1}
    for i in range(50,100):
        #page = random.randint(1,692)
        page_url = url % i
        parse_html(page_url,dir)
    end = datetime.datetime.now()
    print('done cost:',(end-start).seconds,' s')

def parse_html(url,dir):
    html = requests.get(url).text
    soup = BeautifulSoup(html,features='lxml')
    h4  = soup.find_all('h4',{"class":"photo_list_title"})
    for con in h4:
        a = con.find('a')
        detail_url = a['href']
        if len(detail_url)>0:
            img_detail_html = requests.get(detail_url).text
            isoup = BeautifulSoup(img_detail_html,features='lxml')
            img_url = isoup.find('div',{"id":"detailPic"}).find('img')['src']
            #print('pic url',img_url)
            if len(img_url)>0:
                download(img_url,dir)    

#下载pic到指定目录
def download(url,dir):
    dir = dir + '/%s'
    pic = url.split('/')[-1]#按照 / 分隔 取最后一个作为pic name
    r = requests.get(url,stream=True)
    with open(dir % pic,'wb') as d:
        for chunk in r.iter_content(chunk_size=128):
            d.write(chunk)
    print(pic,'success download!')


if __name__ == "__main__":
    # download_demo()
    download_random()