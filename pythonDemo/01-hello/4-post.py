#!/usr/bin/env python3
# -*- coding: utf-8 -*

import requests
import re
import os

def login():
    payload = {'username': 'Morvan', 'password': 'password'}
    r = requests.post('http://pythonscraping.com/pages/cookies/welcome.php', data=payload)
    print(r.cookies.get_dict())

# {'username': 'Morvan', 'loggedin': '1'}
    r = requests.get('http://pythonscraping.com/pages/cookies/profile.php', cookies=r.cookies)
    print(r.text)

def session():
    session = requests.Session()
    payload = {'username': 'Morvan', 'password': 'password'}
    r = session.post('http://pythonscraping.com/pages/cookies/welcome.php', data=payload)
    print(r.cookies.get_dict())
# {'username': 'Morvan', 'loggedin': '1'}
    r = session.get("http://pythonscraping.com/pages/cookies/welcome.php")
    print(r.text)
    print(r.cookies.get_dict())

#下载图片
def download():
    #1.找到目标url
    img_url ="https://morvanzhou.github.io/static/img/description/learning_step_flowchart.png"
    #2.指定下载目录
    os.makedirs('./img/',exist_ok=True)
    #3.下载
    r = requests.get(img_url,stream=True)#stream loading
    with open('./img/test.png','wb') as f:
        for chunk in r.iter_content(chunk_size=32):
            f.write(chunk)

if __name__ == "__main__":
    # login()
    # session()
    download()