#!/usr/bin/env python3
# -*- coding: utf-8 -
import requests
import os
import re
import random
from bs4 import BeautifulSoup
import datetime
import time
import multiprocessing as mp

#多进程分布式爬虫

BASE_URL ="http://image.ngchina.com.cn"
DIR = '/mnt/d/guojiadili/multi_v2'
DIR_S = '/mnt/d/guojiadili/simple'
FILE = DIR + '/%s'

#下载网页job
def get_html_job(url):
   # print('req url : ',url)
    if len(url)>0:
        html = requests.get(url).text
        return html

#解析page页面内容job
def parse_page_job(html):
    soup = BeautifulSoup(html,features='lxml')
    h4  = soup.find_all('h4',{"class":"photo_list_title"})
    detail_urls = [ con.find('a')['href'] for con in h4]
    return detail_urls

#解析图片详情内容job
def parse_detail_job(detail):
    if len(detail)>0:
        isoup = BeautifulSoup(detail,features='lxml')
        if isoup is not None:
           div_soup = isoup.find('div',{"id":"detailPic"})
           if div_soup is not None:
               return div_soup.find('img')['src']
        #return isoup.find('div',{"id":"detailPic"}).find('img')['src']
        #print('pic url',img_url)
        #if len(img_url)>0:
            # img_urls.append(img_url)
    # return img_urls

#下载图片到本地job
def download_img(url):
    pic = url.split('/')[-1]#按照 / 分隔 取最后一个作为pic name
    r = requests.get(url,stream=True)
    with open(FILE % pic,'wb') as d:
        for chunk in r.iter_content(chunk_size=256):
            d.write(chunk)
    print(pic,'success download!')

def download_img_2_dir(url,dir):
    dir_t = dir + '/%s'
    pic = url.split('/')[-1]#按照 / 分隔 取最后一个作为pic name
    r = requests.get(url,stream=True)
    with open(dir_t % pic,'wb') as d:
        for chunk in r.iter_content(chunk_size=256):
            d.write(chunk)
    #print(pic,'success download!')

#多进程爬取国家地理图片main方法
def multi_process_main(n):
    t = time.time()
    # dir = '/mnt/d/guojiadili/multi_v1'
    os.makedirs(DIR,exist_ok=True)
    url = "http://www.ngchina.com.cn/index.php?m=content&c=index&a=lists&catid=669&page=%s"
    mp.freeze_support()
    pool = mp.Pool(8) #Pool 注意大写P
    for i in range(1,n):
        page_html_jobs = [pool.apply_async(get_html_job,((url % i),))]
        page_htmls = [p.get() for p in page_html_jobs]

        page_jobs = [pool.apply_async(parse_page_job,(ph,)) for ph in page_htmls]
        detail_urls = [pj.get() for pj in page_jobs]
        #print('detail urls : ',detail_urls)
        detail_html_jobs = [pool.apply_async(get_html_job,(du,)) for du in detail_urls[0]]
        detail_htmls = [ dhj.get() for dhj in detail_html_jobs]

        img_url_jobs = [pool.apply_async(parse_detail_job,(dh,)) for dh in detail_htmls]
        img_urls = [iuj.get() for iuj in img_url_jobs]

        for u in img_urls:
            pool.apply_async(download_img,(u,)) 
    pool.close()
    pool.join()
    print('multi-p total time: ',time.time()-t,'s')


def multi_process_main_v2(n):
    t = time.time()
    # dir = '/mnt/d/guojiadili/multi_v2'
    os.makedirs(DIR,exist_ok=True)
    url = "http://www.ngchina.com.cn/index.php?m=content&c=index&a=lists&catid=669&page=%s"
    page_urls = [url % i for i in range(1,n)]
    mp.freeze_support()
    pool = mp.Pool(8) #Pool 注意大写P

    page_html_jobs = [pool.apply_async(get_html_job,(pu,)) for pu in page_urls]
    page_htmls = [p.get() for p in page_html_jobs]

    page_jobs = [pool.apply_async(parse_page_job,(ph,)) for ph in page_htmls]
    detail_urls = [pj.get() for pj in page_jobs]
    #print('detail urls : ',detail_urls)
    detail_total_urls = []
    for pdus in detail_urls:
        detail_total_urls += pdus
    detail_html_jobs = ([pool.apply_async(get_html_job,(du,)) for du in detail_total_urls])
    detail_htmls = [ dhj.get() for dhj in detail_html_jobs]

    img_url_jobs = [pool.apply_async(parse_detail_job,(dh,)) for dh in detail_htmls]
    img_urls = [iuj.get() for iuj in img_url_jobs]

    download_jobs = [pool.apply_async(download_img,(u,)) for u in img_urls]
    #[dj.get() for dj in download_jobs]
    # for u in img_urls:
        # pool.apply_async(download_img,(u,)) 
    pool.close()
    pool.join()
    print('multi-v2- cost time:',time.time()-t,'s')


def simple_thread(n):
    t = time.time()
    os.makedirs(DIR_S,exist_ok=True)
    url = "http://www.ngchina.com.cn/index.php?m=content&c=index&a=lists&catid=669&page=%s"
    for i in range(1,n):
        page = get_html_job(url % i)
        detail_urls = parse_page_job(page)
        for du in detail_urls:
            detail_html = get_html_job(du)
            img_url = parse_detail_job(detail_html)
            if len(img_url)>0:
                download_img_2_dir(img_url,DIR_S)
    print('simple-t total time: ',time.time()-t,'s')

if __name__ == "__main__":
    n = 50
    multi_process_main_v2(n)
    #multi_process_main(n)
    #simple_thread(n)
    #两种方式 耗时无差别? 多进程cpu 没有压上去 
    #明天找出原因 -A：把提交 爬取页面-解析-下载 放到了 循环生成页码url内
    # n=5 
    #   multi_process_main_v2 16.64182949066162 s
    #   mutil_process_main    92.43424797058105 s 错误的使用了pool
    #   simple_thread         64.45467686653137 s