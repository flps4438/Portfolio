import pandas as pd
import matplotlib.pyplot as plt
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers
import tensorflow_docs as tfdocs
import tensorflow_docs.plots
import tensorflow_docs.modeling
import plotly.graph_objects as go
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error #均方誤差
from sklearn.metrics import mean_absolute_error #平方絕對誤差
from sklearn.metrics import median_absolute_error
from sklearn.metrics import r2_score
from GenWordReport import genWordReport
import streamlit as st
import base64
import os


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


def build_model(train_dataset):

    model = keras.Sequential([
        layers.Dense(64, activation='relu', input_shape=[len(train_dataset.keys())]),
        layers.Dense(16, activation='relu'),
        layers.Dense(4, activation='relu'),
        layers.Dense(1)
    ])

    optimizer = tf.keras.optimizers.RMSprop(0.001)

    model.compile(loss='mse',
                  optimizer=optimizer,
                  metrics=['acc', 'mse'])

    return model

def nnTrain(choose, train_list, predict, old_data):

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

    data_train, data_test, ans_train, ans_test = train_test_split(x_data, y_data, test_size=0.3)

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

    EPOCHS = 100

    model = build_model(x_train)
    plotter = tfdocs.plots.HistoryPlotter(smoothing_std=2)
    # 圖一 (架構圖)

    st.write("神經網路模型架構圖")

    st.image("./img/" + predict + "Model_plot.png", width=350)

    early_history = model.fit(x_train, y_train,
                              epochs=EPOCHS, validation_split=0.2, verbose=0,
                              callbacks=[tfdocs.modeling.EpochDots()])

    early_his = pd.DataFrame(early_history.history)
    early_his['epoch'] = early_history.epoch
    early_his.tail()

    # 訓練圖 MSE vs epochs

    plotter.plot({'Basic': early_history}, metric = "mse")
    plt.ylim([0, 10])
    plt.ylabel('MSE')
    plt.savefig('nn_mse_error.jpg')
    fig = plt.gcf()
    plt.close(fig)

    st.write("")
    st.write("神經網路訓練 MSE 誤差")
    st.image('nn_mse_error.jpg', width=500)

    y_pred = model.predict(x_test)

    y_test = y_test.reset_index(drop=True)
    y_pred = pd.DataFrame(y_pred, columns=['Pre'])['Pre']

    st.write("")
    st.write("模型誤差如下")

    st.write("mean_squared_error = {:.3}".format(mean_squared_error(y_pred, y_test)))

    st.write("mean_absolute_error = {:.3}".format(mean_absolute_error(y_pred, y_test)))

    st.write("median_absolute_error = {:.3}".format(median_absolute_error(y_pred, y_test)))

    st.write("")
    st.write("R square 誤差 = {:.3f}".format(r2_score(y_pred, y_test)))
    st.write("")

    layout = go.Layout(xaxis=dict(title='實際值'), yaxis=dict(title='預測值'))
    fig = go.Figure(layout=layout)
    fig.add_trace(go.Scatter(x=y_test.reset_index(drop=True), y=y_pred,
                             mode='markers',
                             name='markers'))

    fig.add_trace(go.Scatter(x=[0, 19], y=[0, 19],
                             mode='lines',
                             name='lines'))
    st.write("預測成績與實際成績分布", fig)

    c = abs(y_pred - y_test)

    st.write("error <= 0.5, 共 " + str(len(c[c <= 0.5])) + " 筆資料\t\t約 {:.2%}".format(len(c[c <= 0.5]) / len(c)))
    st.write("error <= 1.5, 共 " + str(len(c[c <= 1.5])) + " 筆資料\t\t約 {:.2%}".format(len(c[c <= 1.5]) / len(c)))
    st.write("error <= 2.5, 共 " + str(len(c[c <= 2.5])) + " 筆資料\t\t約 {:.2%}".format(len(c[c <= 2.5]) / len(c)))

    reportData = [train, test, x_train, x_test, y_train, y_test, model]
    genWordReport(choose, train_list, predict, old_data, reportData)
    os.chdir("../")

    st.header("三、下載訓練報告")
    st.write("")
    st.write("點選下方連結下載訓練報告")

    filename = "NN神經網路_" + predict + "_訓練結果報告.docx"

    word_file = open('File/report.docx', "rb").read()
    b64_file = base64.b64encode(word_file).decode()
    href = f'<a download={filename} href="data:file/doc;base64,{b64_file}">下載報告</a>'
    st.markdown(href, unsafe_allow_html=True)