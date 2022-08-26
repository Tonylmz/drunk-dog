import pymysql
import sys
import os
import pandas as pd
from sqlalchemy import create_engine
import numpy as np
import argparse

import requests
import pandas as pd
from lxml import etree
headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
}
url='https://movie.douban.com/cinema/nowplaying/beijing/'

r=requests.get(url,headers=headers)
#print(r.text)
s=etree.HTML(r.text)
data=s.xpath('(//*/ul/li/a/@title)')
movie_id=s.xpath('(//*/@data-release)')
datap=[]
for i in range(len(movie_id)):
    if int(movie_id[i])>2021:
        datap.append(data[i])
df = pd.DataFrame(datap)
conn = pymysql.connect(host='118.31.14.231', port=3306, user='root', password='66666666', db='drunkDog', charset='utf8')
cursor = conn.cursor()
sql = 'DELETE FROM movie_2022'
cursor.execute(sql)
for i in range(len(list(df[0]))):
    sql = 'INSERT INTO movie_2022(id,name) value(%s,%s)'
    cursor.execute(sql, (i,df[0][i]))
    conn.commit()  # 提交
