import streamlit as st
import pandas as pd
import plotly.graph_objects as go

def show(data, read_data):

    data = pd.DataFrame(data)
    read_data = pd.DataFrame(read_data)

    st.title("資料基本介紹")
    st.write("")

    st.header("一、數據來源")

    st.write("")
    st.write("  1. 資料來源：UC Irvine Machine Learning Repository")
    st.write("  2. 資料主題：Student Performance Data Set")
    st.write("  3. 資料內容：葡萄牙語學生成績及相關資料")
    st.write("  4. 資料位址：https://archive.ics.uci.edu/ml/datasets/Student+Performance")
    st.write("  5. Data (Top 15)：", read_data.head(15))
    st.write("")

    st.header("二、資料探索")

    st.write("")
    st.write("  1. 資料大小：", data.shape)
    column = data.columns
    column = column.insert( 33, 'X')
    column = column.insert( 34, 'X')
    show_column = pd.DataFrame(column[0:7]).T
    show_column = show_column.append(pd.DataFrame(column[7:14]).T, ignore_index=1)
    show_column = show_column.append(pd.DataFrame(column[14:21]).T, ignore_index=1)
    show_column = show_column.append(pd.DataFrame(column[21:28]).T, ignore_index=1)
    show_column = show_column.append(pd.DataFrame(column[28:35]).T, ignore_index=1)
    st.write("  2. 資料欄位：", show_column )
    st.write("")

    st.header("三、欄位介紹")

    st.write("")
    st.write("  1. traveltime： 到學校的通勤時間。 (1~4) (15-, 15-30, 30-60, 60+) (min)")
    st.write("  2. studytime： 每週讀書時間。 (1~4) (2-, 2-5, 5-10, 10+) (hr)")
    st.write("  3. failures：被當的科目。(0~4) (0~3, 4+) (數)")
    st.write("  4. schoolsup：school educational support. (0/1)")
    st.write("  5. Famsup：是否有請家教。 (0/1)")
    st.write("  6. paid： extra paid classes within the course subject. (0/1)")
    st.write("  7. Activities：有無參加課外活動。 (0/1)")
    st.write("  8. nursery：有沒有就讀幼兒園。 (0/1)")
    st.write("  9. higher：有無升學意願。 (0/1)")
    st.write("  10. internet：家中有無網路。(0/1)")
    st.write("  11. romantic：是否有在談戀愛。(0/1)")
    st.write("  12. famrel：家庭關係如何。(1~5) (very bad -> excellent)")
    st.write("  13. freetime：放學後的自由時間。(1~5) (very low -> very high)")
    st.write("  14. goout：與朋友出遊的頻率。(1~5) (very low -> very high)")
    st.write("  15. Dalc：平日喝酒頻率。(1~5) (very low -> very high)")
    st.write("  16. Walc：假日喝酒頻率。(1~5) (very low -> very high)")
    st.write("  17. health：身體健康狀況。(1~5) (very bad -> very good)")
    st.write("  18. absences：翹課次數。(0~93) (次)")
    st.write("  19. G1：第一期成績。(0~20)")
    st.write("  20. G2：第二期成績。(0~20)")
    st.write("  21. G3：第三期成績。(0~20)")
    st.write("")

    st.header("四、資料探索 - 學校、年齡")
    st.write("")

    school_count = data['school'].value_counts().to_frame()
    school_count.rename(index={1: "GP", 2: "MS"}, inplace=True)
    school_count.columns = ['count']

    school_count_layout = go.Layout(title='1.   學校的比較', xaxis=dict(title='學校(school 欄位)'), yaxis=dict(title='數量'))

    school_count_fig = go.Figure( data=[
        go.Bar(
            x=school_count.index,
            y=school_count["count"],
            width=[0.4, 0.4],
        )
    ], layout=school_count_layout)

    st.write(school_count_fig)

    st.write("")

    year_count = data['age'].value_counts().to_frame()
    year_count.columns = ['count']

    year_count_layout = go.Layout(title='2.     年齡的比較', xaxis=dict(title='年齡(age 欄位)'), yaxis=dict(title='數量'))

    year_count_fig = go.Figure(data=[
        go.Bar(
            x=year_count.index,
            y=year_count["count"],
            width=[0.45, 0.45, 0.45, 0.45, 0.45, 0.45, 0.45, 0.45],
            marker_color='rgb(255, 100, 100)'
        )
    ], layout=year_count_layout)

    st.write(year_count_fig)
    st.write("")

    st.header("五、資料前處理")
    st.write("")

    st.write("將欄位的字串變成數字")

    st.write("1. school : GP->1, MS->2")
    st.write("2. sex : F->1, M->2")
    st.write("3. address : U->1, R->2")
    st.write("4. famsize : LE3->1, GT3->2")
    st.write("5. Pstatus : A->1, T->2")
    st.write("6. Mjob : at_home->1, health->2, services->3, teacher->4, other->5")
    st.write("7. Fjob : at_home->1, health->2, services->3, teacher->4, other->5")
    st.write("8. reason : course->1, home->2, reputation->3, other->4")
    st.write("9. guardian : mother->1, father->2, other->3")
    st.write("")
    st.write("將所有 yes/no 的欄位變成 1/0，變動的欄位如下：")
    change_column = ["schoolsup", "famsup", "paid", "activities", "nursery", "higher", "internet", "romantic"]
    change = pd.DataFrame(change_column)
    st.write(change.T)
    st.write("")

    st.header("六、獲取訓練用資料")
    st.write("")
    st.write("尋找各欄位與 G1、G2、G3 的相關係數")

    corr = data.corr()
    relation = round(corr.iloc[30:33, 0:33], 4)  # 取小數後4位
    relation = pd.DataFrame(relation)

    st.dataframe(relation.T.style.background_gradient(subset=['G1', 'G2', 'G3'], cmap='BuGn'))
    st.write("")

    st.write("由上圖可以得知，有 15 個欄位與 G1、G2、G3 較有相關，因此訓練時將只輸入這 15 個欄位")
    st.write("")
    st.write("訓練欄位如下")

    train_column_1=["school", "sex", "age", "address", "Medu"]
    train_column_2=["Fedu", "traveltime", "studytime", "failures", "higher"]
    train_column_3=["internet", "freetime", "Dalc", "Walc", "absences"]
    train_column_4=["G1", "G2", "G3", "X", "X"]

    train = { "0": train_column_1,
              "1": train_column_2,
              "2": train_column_3,
              "3": train_column_4 }

    st.write(pd.DataFrame(train))
    st.write("")

    data.pop("famsize")
    data.pop("Pstatus")
    data.pop("Mjob")
    data.pop("Fjob")
    data.pop("guardian")
    data.pop("reason")
    data.pop("schoolsup")
    data.pop("famsup")
    data.pop("paid")
    data.pop("activities")
    data.pop("nursery")
    data.pop("romantic")
    data.pop("famrel")
    data.pop("goout")
    data.pop("health")

    st.write("")

    st.write("經調整後的資料大小：", data.shape)
