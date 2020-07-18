#!/usr/bin/env python3
# -*- coding: utf-8 -*-
from bs4 import BeautifulSoup
from urllib.request import urlopen #urllib python自带模块
from urllib.parse import quote
import re
import random
import ssl
import sys

ssl._create_default_https_context = ssl._create_unverified_context

def get_url(url):
    #print('\n','url:    ',url)
    return urlopen(url).read().decode('utf-8')
    #return urlopen(quote(url)).read().decode('utf-8')


base_url = 'https://baike.baidu.com'
#base_url = "https://baike.baidu.com"
his = ['/item/%E7%BD%91%E7%BB%9C%E7%88%AC%E8%99%AB/5162711']

#简单的爬取 百科 网络爬虫 词条
#目标网页格式
#<a target = "_blank" href = "/item/%E8%9C%98%E8%9B%9B/8135707" data-lemmaid = "8135707" > 蜘蛛 < /a >
#<a target = "_blank" href = "/item/%E8%A0%95%E8%99%AB" > 蠕虫 < /a >
#<a target = "_blank" href = "/item/%E9%80%9A%E7%94%A8%E6%90%9C%E7%B4%A2%E5%BC%95%E6%93%8E" > 通用搜索引擎 < /a >
def simple():
    print('\n',"===== simple start ====")
    url = base_url + his[-1]
    html = get_url(url)
    soup = BeautifulSoup(html,features='html.parser')
    print(soup.find('h1').get_text(),'  url : ',his[-1])

    sub_urls = soup.find_all("a", {"target": "_blank", "href": re.compile(
        "/item/(%.{2})+$")})  # re.compile("/item/(%.{2})+$")
    if len(sub_urls) != 0:
        his.append(random.sample(sub_urls, 1)[0]['href'])
    else :
        his.pop()
    print(his)  
    print('\n', "===== simple end ====")

def loop_query():
    print('\n', "===== loop_query start ====")
    pattern = "https://"
    for i in range(20):
        # if (pattern in his[-1]):
        #    url = his[-1]
        # else :   
        #     url = base_url + his[-1]
        if (pattern in his[-1]):# 以 http 开头的item 链接不处理
            continue
        else:
            url = base_url + his[-1]
        html = get_url(url)
        soup = BeautifulSoup(html, features='html.parser')
        print(i,soup.find('h1').get_text(),'    url: ',his[-1])
        sub_urls = soup.find_all("a", {"target": "_blank", "href": re.compile(
            "/item/(%.{2})+$")})  # re.compile("/item/(%.{2})+$")
        if len(sub_urls) != 0:
            his.append(random.sample(sub_urls, 1)[0]['href'])
        else:
            his.pop()
    print('\n', "===== loop_query end ====")

if __name__ == "__main__":
    simple()
    loop_query()
# 问题记录
# Q:UnicodeEncodeError: 'ascii' codec can't encode characters in position 10-13: ordinal not in range(128)
# A:链接中有中文 原来 his=['/item/中文']
