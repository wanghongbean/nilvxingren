#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import requests
import webbrowser

BASE_URL = "http://www.baidu.com/s"
SEARCH_PARAM = {"wd": "莫烦Python"} 

def get_test():
    r = requests.get(BASE_URL,params=SEARCH_PARAM)
    print(r.url)
    webbrowser.open(r.url)

def post_test(url,data):
    # url = "http://pythonscraping.com/files/processing.php"
    #url = 'http://pythonscraping.com/files/processing.php'
    # data = {'firstname': '河狸', 'lastname': '家'}
    #data = {'firstname': '莫烦', 'lastname': '周'}
    r = requests.post(url,data=data)
    print(r.text)

def file_test():
    file = {'uploadFile': open(
        '/Users/labu/Downloads/pic/unsplash-Jack Millard.jpg', 'rb')}
    r = requests.post(
        'http://pythonscraping.com/files/processing2.php', files=file)
    print(r.text)    
    #Sorry, there was an error uploading your file. 应该是服务器的问题，没有通过自启服务器test爬虫应该很少用到上传文件


if __name__ == "__main__":
    # get_test()
    data = {'firstname': '河狸', 'lastname': '家'}
    url = "http://localhost:8080/user"
    #post_test(url,data)
    file_test()

