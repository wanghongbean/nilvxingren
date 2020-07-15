#!/usr/bin/env python3
# -*- coding: utf-8 -*-
from bs4 import BeautifulSoup
from urllib.request import urlopen
from urllib.parse import quote
import re
import random
import ssl
import sys

#ssl._create_default_https_context = ssl._create_unverified_context

def get_url(url):
    return urlopen(quote(url)).read().decode('utf-8')


base_url = "https://baike.baidu.com"
his = ["/item/%E7%BD%91%E7%BB%9C%E7%88%AC%E8%99%AB/5162711"]

#简单的爬取 百科 网络爬虫 词条
def simple():
    url = base_url + his[-1]
    print(url)
    html = get_url(quote(url))
    soup = BeautifulSoup(html,features='html.parser')
    #soup = BeautifulSoup(html,features='html.parser',from_encoding="utf-8")
    print(soup.find('h1').get_text(),'  url : ',his[-1])


if __name__ == "__main__":
    simple()
# 问题记录
# UnicodeEncodeError: 'ascii' codec can't encode characters in position 10-13: ordinal not in range(128)
