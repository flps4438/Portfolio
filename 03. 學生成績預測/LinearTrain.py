import base64
import os

import pandas as pd
from sklearn import linear_model
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.metrics import mean_absolute_error
from sklearn.metrics import median_absolute_error
from sklearn.model_selection import train_test_split

import plotly.graph_objects as go
import streamlit as st
from GenWordReport import genWordReport


def replace(putData, train_list):

    for i in train_list :

        if i == 'school':

            putData['school'].replace('GP', 1, inplace=True)
            putData['school'].replace('MS', 2, inplace=True)

        if i == 'sex':

            putData['sex'].replace('F', 1, inplace=True)
            putData['sex'].replace('M', 2, inplace=True)

        if i == 'address':

            putData['address'].replace('U', 1, inplace=True)
            putData['address'].replace('R', 2, inplace=True)

def linerTrain(choose, train_list, predict, old_data):

    old_data = pd.DataFrame(old_data)

    st.title("訓練模型 - " + choose)

    st.write("")

    st.write("選擇要訓練的模型：", choose)
    st.write("要訓練的參數：", pd.DataFrame(train_list).T)
    st.write("要訓練的結果：", predict)

    st.header("一、資料前處理")
    st.write("")
    st.write("1. 讀取原本的資料 : ", old_data.shape, old_data)
    st.write("")

    x_data = old_data[train_list]
    y_data = old_data[predict]

    train_data = pd.concat([x_data, y_data], axis=1)

    st.write("2. 將資料只留下要訓練的欄位 : ", train_data.shape, train_data)
    st.write("")

    replace(train_data, train_list)

    x_data = train_data[train_list]
    y_data = train_data[predict]

    st.write("3. 轉換訓練資料的欄位 : ", train_data.shape, train_data)
    st.write("")

    data_train, data_test, ans_train, ans_test = train_test_split( x_data, y_data, test_size=0.3)

    train = pd.concat([data_train, ans_train], axis=1)
    test = pd.concat([data_test, ans_test], axis=1)

    st.write("4. 切割資料為訓練集與測試集，以 7 : 3 做切割點")
    st.write("")
    st.write("訓練用資料：", train.shape, train)
    st.write("測試用資料：", test.shape, test)
    st.write("")

    st.header("二、訓練模型")
    st.write("")

    x_train = data_train
    x_test = data_test
    y_train = ans_train
    y_test = ans_test

    regr = linear_model.LinearRegression()
    regr = regr.fit(x_train, y_train)

    y_pred = regr.predict(x_test)

    variable_name = pd.Series(x_train.columns)
    variable = pd.Series(regr.coef_)
    variable_table = pd.concat([variable_name, variable], axis=1)
    variable_table.columns = ["name", "value"]

    st.write("參數訓練結果")
    st.dataframe(variable_table.style.background_gradient(subset=['value'], cmap='BuGn'))

    regression = predict + " = "

    for i in range(0, len(variable)) :

        regression = regression + variable_name[i]
        regression = regression + " x ( "
        regression = regression + str(round(variable[i], 3 )) + " ) "

        if i != len(variable) - 1 :

            regression = regression + " + "

    st.write("")
    st.write("回歸式")
    st.write(regression)

    st.write("")
    st.write("模型誤差如下")

    st.write("mean_squared_error = {:.3}".format(mean_squared_error(y_pred, y_test)))

    st.write("mean_absolute_error = {:.3}".format(mean_absolute_error(y_pred, y_test)))

    st.write("median_absolute_error = {:.3}".format(median_absolute_error(y_pred, y_test)))

    st.write("")
    st.write("R square 誤差 = {:.3f}".format(r2_score(y_pred, y_test)))
    st.write("")


    layout = go.Layout(title='預測成績與實際成績分布', xaxis=dict(title='實際值'), yaxis=dict(title='預測值'))
    fig = go.Figure(layout=layout)
    fig.add_trace(go.Scatter(x=y_test, y=y_pred,
                             mode='markers',
                             name='markers'))

    fig.add_trace(go.Scatter(x=[0, 19], y=[0, 19],
                             mode='lines',
                             name='lines'))
    st.write("預測成績與實際成績分布", fig)

    c = abs(y_pred - y_test)

    st.write("error <= 0.5, 共 " + str(len(c[c <= 0.5])) + " 筆資料\t\t約 {:.2%}".format(len(c[c <= 0.5]) / len(c)) )
    st.write("error <= 1.5, 共 " + str(len(c[c <= 1.5])) + " 筆資料\t\t約 {:.2%}".format(len(c[c <= 1.5]) / len(c)) )
    st.write("error <= 2.5, 共 " + str(len(c[c <= 2.5])) + " 筆資料\t\t約 {:.2%}".format(len(c[c <= 2.5]) / len(c)) )

    reportData = [train, test, x_train, x_test, y_train, y_test, regr]
    genWordReport( choose, train_list, predict, old_data, reportData)
    os.chdir("../")

    st.header("三、下載訓練報告")
    st.write("")
    st.write("點選下方連結下載訓練報告")

    filename = "回歸模型_" + predict + "_訓練結果報告.docx"

    word_file = open('File/report.docx', "rb").read()
    b64_file = base64.b64encode(word_file).decode()
    href = f'<a download={filename} href="data:file/doc;base64,{b64_file}">下載報告</a>'
    st.markdown(href, unsafe_allow_html=True)

    # csv = pd.read_csv('student-por.csv').to_csv()