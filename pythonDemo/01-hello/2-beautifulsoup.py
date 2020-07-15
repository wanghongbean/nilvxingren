#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from urllib.request import urlopen
import ssl
import re
from bs4 import BeautifulSoup


# 使用urllib.urlopen一个 https 的时候会验证一次 SSL证书,通过导入ssl模块把证书验证改成不用验证就行了
ssl._create_default_https_context = ssl._create_unverified_context

hello_url = "https://morvanzhou.github.io/static/scraping/basic-structure.html"
css_url = "https://morvanzhou.github.io/static/scraping/list.html"
regular_url = "https://morvanzhou.github.io/static/scraping/table.html"

def get_url(url):
    return urlopen(url).read().decode('utf-8')


def test_beautifulsoup():
    #beautifulsoup html解析
    html = get_url(hello_url)
    #soup = BeautifulSoup(html,features='lxml') # bs4.FeatureNotFound: Couldn't find a tree builder with the features you requested: lxml. Do you need to install a parser library?
    soup = BeautifulSoup(html,features='html.parser')
    print(soup.h1)
    all = soup.find_all('a')
    #我们能用像 Python 字典的形式, 用 key 来读取 l["href"]
    res = [l["href"] for l in all]
    print("\n",res)
    print("\n","================")
    
    #解析css
    css = get_url(css_url)
    c_soup = BeautifulSoup(css,features='html.parser')
    month = c_soup.find_all('li',{"class":"month"})# 取被标记class=month的li标签
    for m in month:
        print(m.get_text())

    print("\n","================")
    #regular 正则表达式
    re_html = get_url(regular_url)
    links_html = BeautifulSoup(re_html,features='html.parser')
    img_links = links_html.find_all('img', {"src": re.compile('.*?\.jpg')})#正则取出jpg格式的img标签
    for l in img_links:
        print("\n",l['src'])

    print("\n", "================")
    start = links_html.find_all('a', {'href': re.compile(
        'https://morvan.*')})  # 正则取出 以https://morvan. 开头的a标签
    for s in start:
        print("\n",s['href'])
if __name__ == "__main__":
    test_beautifulsoup()
