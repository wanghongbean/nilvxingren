#!/usr/bin/env python3
# -*- coding: utf-8 -*-


from urllib.request import urlopen
import ssl
import re
# 使用urllib.urlopen一个 https 的时候会验证一次 SSL证书,通过导入ssl模块把证书验证改成不用验证就行了
ssl._create_default_https_context = ssl._create_unverified_context
hello_url = "https://morvanzhou.github.io/static/scraping/basic-structure.html"
html = urlopen(hello_url).read().decode('utf-8')
print(html)

titile = re.findall(r"<title>(.+?)</title>",html)
print("\n web page's tital is : ",titile[0])

p = re.findall(r"<p>(.*?)</p>",html,flags=re.DOTALL)
print("\n web page p is : ",p[0])

href = re.findall(r'href="(.*?)"',html)
print("\n all urls : ",href)

bd = urlopen("https://www.baidu.com").read().decode('utf-8')
print("\n baidu page : ",bd)
