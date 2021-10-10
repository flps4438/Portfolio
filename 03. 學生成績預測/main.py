import pandas as pd
import streamlit as st
from Introduction import show
from Predict import predict
from Train import train_page
from Conclusion import conclusion
from Bot import bot

read_data = pd.read_csv('student-por.csv')
old_data = read_data.copy()
data = pd.DataFrame(read_data)

def replace(putData):

    putData['school'].replace('GP', 1, inplace=True)
    putData['school'].replace('MS', 2, inplace=True)
    putData['sex'].replace('F', 1, inplace=True)
    putData['sex'].replace('M', 2, inplace=True)
    putData['address'].replace('U', 1, inplace=True)
    putData['address'].replace('R', 2, inplace=True)
    putData['famsize'].replace('LE3', 1, inplace=True)
    putData['famsize'].replace('GT3', 2, inplace=True)
    putData['Pstatus'].replace('A', 1, inplace=True)
    putData['Pstatus'].replace('T', 2, inplace=True)
    putData['Mjob'].replace('at_home', 1, inplace=True)
    putData['Mjob'].replace('health', 2, inplace=True)
    putData['Mjob'].replace('services', 3, inplace=True)
    putData['Mjob'].replace('teacher', 4, inplace=True)
    putData['Mjob'].replace('other', 5, inplace=True)
    putData['Fjob'].replace('at_home', 1, inplace=True)
    putData['Fjob'].replace('health', 2, inplace=True)
    putData['Fjob'].replace('services', 3, inplace=True)
    putData['Fjob'].replace('teacher', 4, inplace=True)
    putData['Fjob'].replace('other', 5, inplace=True)
    putData['reason'].replace('course', 1, inplace=True)
    putData['reason'].replace('home', 2, inplace=True)
    putData['reason'].replace('reputation', 3, inplace=True)
    putData['reason'].replace('other', 4, inplace=True)
    putData['guardian'].replace('mother', 1, inplace=True)
    putData['guardian'].replace('father', 2, inplace=True)
    putData['guardian'].replace('other', 3, inplace=True)

replace(data)

print(data)

st.sidebar.title("學生成績預測")

st.sidebar.header("選擇功能")
choose_option = st.sidebar.radio("選擇功能", ["資料基本介紹", "訓練模型", "預測結果", "結論", "Line Bot"])

if choose_option == "訓練模型" :

    st.sidebar.header("模型類別")
    choose_module = st.sidebar.selectbox("選擇訓練方式", ['回歸模型', '決策樹', 'NN模型'], index = 0 )

    chain_range = st.sidebar.selectbox("選擇預測成績", ["G1", "G2", "G3"], index=0)


    if chain_range == "G3":

        choose_column = ["school", "sex", "age", "address", "Medu", "Fedu", "traveltime", "studytime",
                        "failures", "higher", "internet", "freetime", "Dalc", "Walc", "absences", "G1", "G2"]

    elif chain_range == "G2":

        choose_column = ["school", "sex", "age", "address", "Medu", "Fedu", "traveltime", "studytime",
                        "failures", "higher", "internet", "freetime", "Dalc", "Walc", "absences", "G1"]

    elif chain_range == "G1":

        choose_column = ["school", "sex", "age", "address", "Medu", "Fedu", "traveltime", "studytime",
                        "failures", "higher", "internet", "freetime", "Dalc", "Walc", "absences"]

    if choose_module == "回歸模型" or choose_module == "決策樹":

        train_label_list = st.sidebar.multiselect("選擇要放入的參數", choose_column, default=choose_column)

    else :

        train_label_list = choose_column
        st.sidebar.write("放入的參數 (鎖定)", choose_column)
        st.sidebar.write("NN 模型由於報告輸出有 heroku 不支持套件，因此鎖定放入參數欄位")


    if st.sidebar.button("開始訓練"):

        train_page(choose_module, train_label_list, chain_range, old_data)

    else:
        st.title("訓練模型 - " + choose_module)
        st.write("")
        st.write("請點選左方按鈕開始訓練")


elif choose_option == "預測結果":

    predict(old_data)

elif choose_option == "資料基本介紹":

    show(data, old_data)

elif choose_option == "結論":

    conclusion()

elif choose_option == "Line Bot":

    bot()