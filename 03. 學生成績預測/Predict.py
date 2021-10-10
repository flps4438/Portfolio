import base64

import streamlit as st
import pandas as pd

from LinearModelPredict import linearPredict
from TreePredict import treePredict
from NNPredict import nnPredict
from GenExcel import genExcel

def replace(putData):

    putData['school'].replace('GP', 1, inplace=True)
    putData['school'].replace('MS', 2, inplace=True)
    putData['sex'].replace('F', 1, inplace=True)
    putData['sex'].replace('M', 2, inplace=True)
    putData['address'].replace('U', 1, inplace=True)
    putData['address'].replace('R', 2, inplace=True)

def predict(old_data):

    old_data = pd.DataFrame(old_data)

    st.sidebar.header("選擇預測成績")
    predict = st.sidebar.selectbox("選擇預測成績", ["G1", "G2", "G3"], index=0)

    st.sidebar.header("輸入相關資料")

    school = st.sidebar.selectbox("school", old_data['school'].unique())
    sex = st.sidebar.selectbox("sex", ["M", "F"])
    age = st.sidebar.selectbox("age", [15, 16, 17, 18, 19, 20, 21, 22])
    address = st.sidebar.selectbox("address", ["U", "R"])
    Medu = st.sidebar.selectbox("Medu (at_home, health, services, teacher, other)", [1, 2, 3, 4, 5])
    Fedu = st.sidebar.selectbox("Fedu (at_home, health, services, teacher, other)", [1, 2, 3, 4, 5])
    traveltime = st.sidebar.selectbox("traveltime (15-, 15-30, 30-60, 60+)(min)", [1, 2, 3, 4])
    studytime = st.sidebar.selectbox("studytime (2-, 2-5, 5-10, 10+)(hr)", [1, 2, 3, 4])
    failures = st.sidebar.selectbox("failures (0-3,4+)", [0, 1, 2, 3, 4])
    higher = st.sidebar.selectbox("higher", [0, 1])
    internet = st.sidebar.selectbox("internet", [0, 1])
    freetime = st.sidebar.selectbox("freetime(very bad -> very high)", [1, 2, 3, 4, 5])
    Dalc = st.sidebar.selectbox("Dalc", [1, 2, 3, 4, 5])
    Walc = st.sidebar.selectbox("Walc", [1, 2, 3, 4, 5])
    absences = st.sidebar.slider("absences", min_value=0, max_value=93)

    if predict == "G2" or predict == "G3":

        G1 = st.sidebar.slider("G1", min_value=0, max_value=20)

    if predict == "G3":

        G2 = st.sidebar.slider("G2", min_value=0, max_value=20)

    st.title("預測結果")
    st.write("")
    st.write("要用來預測的資料僅輸入相關性高的15個欄位(詳見資料說明)")
    st.write("")

    st.header("確認輸入")
    st.write("")

    st.write("School : " + school)
    st.write("Sex : " + sex)
    st.write("Age : " + str(age))
    st.write("Address : " + address)
    st.write("Medu : " + str(Medu))
    st.write("Fedu : " + str(Fedu))
    st.write("Traveltime : " + str(traveltime))
    st.write("Studytime : " + str(studytime))
    st.write("Failures : " + str(failures))
    st.write("Higher : " + str(higher))
    st.write("Internet : " + str(internet))
    st.write("Freetime : " + str(freetime))
    st.write("Dalc : " + str(Dalc))
    st.write("Walc : " + str(Walc))
    st.write("Absences : " + str(absences))

    if predict == "G2" or predict == "G3" :
        st.write("G1 : " + str(G1))

    if predict == "G3" :
        st.write("G2 : " + str(G2))

    st.write("")

    st.header("預測")

    st.write("")
    st.write("點選下方按鈕開始預測")
    st.write("")

    if st.button("開始預測 - " + predict) :

        st.write("")

        test_data = {

            "school" : [school],
            "sex" : [sex],
            "age" : [age],
            "address" : [address],
            "Medu" : [Medu],
            "Fedu" : [Fedu],
            "traveltime" : [traveltime],
            "studytime" : [studytime],
            "failures" : [failures],
            "higher" : [higher],
            "internet" : [internet],
            "freetime" : [freetime],
            "Dalc" : [Dalc],
            "Walc" : [Walc],
            "absences" : [absences]
        }


        if predict == "G2" or predict == "G3":

            test_data['G1'] = G1

        if predict == "G3" :

            test_data['G2'] = G2

        test_data = pd.DataFrame(test_data)

        st.header("一、將輸入資料生成 DataFrame")
        st.write(test_data)
        st.write("")

        old_test_data = test_data.copy()

        replace(test_data)
        st.header("二、將輸入資料做資料轉換 (轉換為數字)")
        st.write(test_data)
        st.write("")

        predictAns = pd.DataFrame()

        st.header("三、回歸模型預測")
        predictAns = linearPredict(predict, test_data)

        st.write("")

        st.header("四、決策樹模型預測")
        predictAns = predictAns.append(treePredict(predict, test_data))

        st.write("")

        st.header("五、NN網路模型預測")
        predictAns = predictAns.append(nnPredict(predict, test_data))


        predictAns = predictAns.T

        st.header("六、比較")
        st.write("")
        st.write("各模型結果比較")
        st.table(predictAns)

        genExcel(old_test_data, predictAns)

        st.header("七、下載報告")
        st.write("")

        filename = "各模型_" + predict + "_預測結果報告.xlsx"

        word_file = open('Predict_Result.xlsx', "rb").read()
        b64_file = base64.b64encode(word_file).decode()
        href = f'<a download={filename} href="data:file/xlsx;base64,{b64_file}">下載報告</a>'
        st.markdown(href, unsafe_allow_html=True)