import base64

from sklearn import tree
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.metrics import mean_absolute_error
from sklearn.metrics import median_absolute_error
from sklearn.tree import plot_tree
import streamlit as st
from DataProcess import process_data
import plotly.graph_objects as go
import pandas as pd
import matplotlib.pyplot as plt

def treePredict(predict, test):

    x_train, y_train, x_test, y_test = process_data(predict)

    clf = tree.DecisionTreeClassifier(max_depth=5)
    clf = clf.fit( x_train, y_train )
    y_pred = clf.predict(x_test)

    # print( 'y_predict : ', y_pred )
    # print( 'y_test : ', y_test )

    # print( tree.export_text(clf) )
    st.write("")
    st.write("<h3>1. 載入模組完成</h3>", unsafe_allow_html=True)
    st.write("")

    x_train = pd.DataFrame(x_train)

    st.write("樹的結構 (因圖片過大，請點選下方下載觀看)")

    grade = [str(i) for i in list(set(y_train))]
    fig, ax = plt.subplots(figsize=(180,15))
    plot_tree(clf, ax = ax, fontsize=15, feature_names=x_train.columns, class_names=grade, filled=True, node_ids=False )
    plt.savefig('./img/tree_structure.png')

    filename = 'tree_structure.png'

    img_file = open('img/tree_structure.png', "rb").read()
    b64_file = base64.b64encode(img_file).decode()
    href = f'<a download={filename} href="data:file/png;base64,{b64_file}">下載決策樹結構圖</a>'
    st.markdown(href, unsafe_allow_html=True)

    st.write("")
    st.write("<h3>2. 模組誤差如下<h3>", unsafe_allow_html=True)

    st.write("mean_squared_error = {:.2f}".format(mean_squared_error(y_pred, y_test)))
    st.write("mean_absolute_error = {:.2f}".format(mean_absolute_error(y_pred, y_test)))
    st.write("median_absolute_error = {:.2f}".format(median_absolute_error(y_pred, y_test)))

    st.write("")
    st.write("<h3>3. 預測成績與實際成績分布<h3>", unsafe_allow_html=True)

    layout = go.Layout(xaxis=dict(title='實際值'), yaxis=dict(title='預測值'))
    fig = go.Figure(layout=layout)
    fig.add_trace(go.Scatter(x=y_test, y=y_pred,
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

    pred = clf.predict(test)
    st.write("<h3>預測同學 " + predict + " 成績為 : {:.2f}".format(pred[0]) + "<h3>", unsafe_allow_html=True)

    predictAns = pd.DataFrame({'預測成績': "{:.2f}".format(pred[0]),
                               "MSE": "{:.2f}".format(mean_squared_error(y_pred, y_test)),
                               "MAE": "{:.2f}".format(mean_absolute_error(y_pred, y_test)),
                               "R^2": "{:.3f}".format(r2_score(y_pred, y_test)),
                               "誤差 <= 0.5": "{:.2%}".format(len(c[c <= 0.5]) / len(c)),
                               "<= 0.5 人數": str(len(c[c <= 0.5])),
                               "誤差 <= 1.5": "{:.2%}".format(len(c[c <= 1.5]) / len(c)),
                               "<= 1.5 人數": str(len(c[c <= 1.5])),
                               "誤差 <= 2.5": "{:.2%}".format(len(c[c <= 2.5]) / len(c)),
                               "<= 2.5 人數": str(len(c[c <= 2.5]))}, index=['決策樹'])
    return predictAns