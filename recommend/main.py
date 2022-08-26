import pymysql
import sys
import os
BASE_DIR = os.path.dirname(os.path.dirname(os.path.dirname(__file__)))
sys.path.append(BASE_DIR)
import pandas as pd
from sqlalchemy import create_engine
import numpy as np
import argparse



def DB_item(vector):
    #连接数据库，将数据读取为dataframe的格式
    conn = create_engine("mysql+pymysql://root:1234@118.31.14.231:3306/drunkDog")
    sql = 'select * from drunkDog.movie'
    file = pd.read_sql(sql,conn)
    genre = file['category']
    movie_id = file['movie_id']
    score = file['score']
    score = score.to_numpy().astype(float)
    one_hot_vector=np.zeros((genre.size,27))
    for i in range(genre.size):
        genres = genre[i].split("|")
        for j in range(len(genres)-2):
            one_hot_vector[i][int(genres[j+1])] += 1-j/10
    vector = vector/np.max(vector)
    similarity = np.dot(vector, one_hot_vector.T)/np.linalg.norm(vector, ord=2)/np.linalg.norm(one_hot_vector, ord=2, axis=1)+0.5*score/10
    #计算余弦相似度
    conn = pymysql.connect(host='118.31.14.231', port=3306, user='root', password='1234', db='drunkDog', charset='utf8')
    cursor = conn.cursor()
    sql = 'DELETE FROM python_movie'
    cursor.execute(sql)
    try:
        for i in range(10):
            sql = 'INSERT INTO python_movie(id,movie_id) value(%s,%s)'
            cursor.execute(sql, (i, movie_id[similarity.argsort()[-1-i]]))
            conn.commit()  # 提交
    except pymysql.Error as err:
        conn.rollback()  # 回滚
        print(err)
    finally:
        conn.close()

    #return movie_id[similarity.argsort()[-11:-1]]


def DB_user(vector):
    #连接数据库，将数据读取为dataframe的格式
    conn = create_engine("mysql+pymysql://root:1234@118.31.14.231:3306/drunkDog")
    sql='select * from drunkDog.movie'
    file=pd.read_sql(sql,conn)
    genre=file['category']
    movie_id=file['movie_id']
    score=file['score']
    score = score.to_numpy().astype(float)
    one_hot_vector=np.zeros((genre.size,27))
    for i in range(genre.size):
        genres=genre[i].split("|")
        for j in range(len(genres)-2):
            one_hot_vector[i][int(genres[j+1])]+=1-j/10
    vector=vector/np.max(vector)
    similarity=np.dot(vector,one_hot_vector.T)/np.linalg.norm(vector,ord=2)/np.linalg.norm(one_hot_vector,ord=2,axis=1)+0.5*score/10
    #计算余弦相似度
    conn=pymysql.connect(host='118.31.14.231',port=3306,user='root',password='1234',db='drunkDog',charset='utf8')
    cursor=conn.cursor()
    sql = 'DELETE FROM python_user'
    cursor.execute(sql)
    try:
        for i in range(10):
            sql = 'INSERT INTO python_user(id,movie_id) value(%s,%s)'
            cursor.execute(sql, (i, movie_id[similarity.argsort()[-1-i]]))
            conn.commit()  # 提交
    except pymysql.Error as err:
        conn.rollback()  # 回滚
        print(err)
    finally:
        conn.close()

    #return movie_id[similarity.argsort()[-11:-1]]

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description="user_or_item")
    parser.add_argument('--user-id', dest='user_id', type=int, default=16, required=False)
    parser.add_argument('--movie-id', dest='movie_id', default=0, type=int, required=False)
    args = parser.parse_args()
    v_u=np.zeros(27)
    v_i=np.zeros(27)
    if args.user_id != 0:
        conn = create_engine("mysql+pymysql://root:1234@118.31.14.231:3306/drunkDog")
        sql = """select * from drunkDog.user_tag where user_id = '{}'""".format(args.user_id)
        file=pd.read_sql(sql,conn)
        gene=file["user_tag"]
        weight=file["user_weight"]
        for i in range(len(list(gene))):
            if list(gene)[i]<27:
                v_u[list(gene)[i]] += list(weight)[i]
        DB_user(v_u)
    if args.movie_id != 0:
        conn = create_engine("mysql+pymysql://root:1234@118.31.14.231:3306/drunkDog")
        sql = """select * from drunkDog.movie where movie_id = '{}'""".format(args.movie_id)
        file=pd.read_sql(sql,conn)
        gene=file["category"]
        genres = gene[0].split("|")
        for j in range(len(genres) - 2):
            v_i[int(genres[j+1])] +=1
        DB_item(v_i)