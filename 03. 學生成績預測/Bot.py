import streamlit as st

def bot():

    st.title("Line 聊天機器人")
    st.write("")
    st.header("掃描下方 QR code 加入官方帳號")
    st.write("")
    st.image("bot.jpg")
    st.write("")
    st.header("關於指令")
    st.write("")

    st.write("-指令 / 指令表 - 查看所有指令")
    st.write("-開始預測 - 開始預測")
    st.write("-進度 - 目前測驗進度")
    st.write("-輸入 - 使用者目前測驗輸入之結果")
    st.write("-回歸 - 以回歸模型預測G3之結果")
    st.write("-決策樹 - 以決策樹模型預測G3之結果")
    st.write("-神經網路 - 以神經網路模型預測G3之結果")
    st.write("-你好 - 台灣打招呼起手式")
    st.write("-機器人 - 跟你說點悄敲話")
    st.write("-Hi - 國際通用打招呼")
    st.write("-閃電 - 本機器人的大絕")
    st.write("")
    st.write("* 開始預測成績後請依照指示點選或輸入相關資訊")