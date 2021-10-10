import pandas as pd
from tensorflow import keras
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.metrics import mean_absolute_error
from sklearn.metrics import median_absolute_error
from DataProcess import process_data
import streamlit as st
import plotly.graph_objects as go

def nnPredict(predict, test):

    x_train, y_train, x_test, y_test = process_data(predict)

    st.write("")

    modelName = predict + 'model'
    premodel = keras.models.load_model(modelName)
    print('Loading the model back!!')

    y_pred = premodel.predict(x_test)

    st.write("<h3>1. 載入模組完成，模型參數如下</h3>", unsafe_allow_html=True)

    st.write("神經網路模型架構圖")
    st.image("./img/" + predict + "Model_plot.png", width=350)

    st.write("")
    st.write("<h3>2. 模組誤差如下<h3>", unsafe_allow_html=True)

    st.write("mean_squared_error = {:.2f}".format(mean_squared_error(y_pred, y_test)))
    st.write("mean_absolute_error = {:.2f}".format(mean_absolute_error(y_pred, y_test)))
    st.write("median_absolute_error = {:.2f}".format(median_absolute_error(y_pred, y_test)))

    y_test = y_test.reset_index(drop=True)
    y_pred = pd.DataFrame(y_pred, columns=['Pre'])['Pre']

    st.write("")
    st.write("<h3>3. 預測成績與實際成績分布<h3>", unsafe_allow_html=True)

    layout = go.Layout(xaxis=dict(title='實際值'), yaxis=dict(title='預測值'))
    fig = go.Figure(layout=layout)
    fig.add_trace(go.Scatter(x=y_test.reset_index(drop=True), y=y_pred,
                             mode='markers',
                             name='markers'))

    fig.add_trace(go.Scatter(x=[0, 19], y=[0, 19],
                             mode='lines',
                             name='lines'))

    st.write(fig)
    st.write("")

    c = abs(y_pred - y_test)

    st.write("<h3>4. 誤差分布<h3>", unsafe_allow_html=True)

    st.write("error <= 0.5, 共 " + str(len(c[c <= 0.5])) + " 筆資料\t\t約 {:.2%}".format(len(c[c <= 0.5]) / len(c)))
    st.write("error <= 1.5, 共 " + str(len(c[c <= 1.5])) + " 筆資料\t\t約 {:.2%}".format(len(c[c <= 1.5]) / len(c)))
    st.write("error <= 2.5, 共 " + str(len(c[c <= 2.5])) + " 筆資料\t\t約 {:.2%}".format(len(c[c <= 2.5]) / len(c)))

    st.write("")
    st.write("<h3>5. R square 誤差 = {:.3f}<h3>".format(r2_score(y_pred, y_test)), unsafe_allow_html=True)

    st.write("")
    st.write("<h3>6. 預測結果<h3>", unsafe_allow_html=True)

    pred = pd.DataFrame(premodel.predict(test))

    st.write("<h3>預測同學 " + predict + " 成績為 : {:.2f}".format(pred[0][0]) + "<h3>", unsafe_allow_html=True)

    predictAns = pd.DataFrame({'預測成績': "{:.2f}".format(pred[0][0]),
                               "MSE": "{:.2f}".format(mean_squared_error(y_pred, y_test)),
                               "MAE": "{:.2f}".format(mean_absolute_error(y_pred, y_test)),
                               "R^2": "{:.3f}".format(r2_score(y_pred, y_test)),
                               "誤差 <= 0.5": "{:.2%}".format(len(c[c <= 0.5]) / len(c)),
                               "<= 0.5 人數": str(len(c[c <= 0.5])),
                               "誤差 <= 1.5": "{:.2%}".format(len(c[c <= 1.5]) / len(c)),
                               "<= 1.5 人數": str(len(c[c <= 1.5])),
                               "誤差 <= 2.5": "{:.2%}".format(len(c[c <= 2.5]) / len(c)),
                               "<= 2.5 人數": str(len(c[c <= 2.5]))}, index=['NN 網路'])
    return predictAns