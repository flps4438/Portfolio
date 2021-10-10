import pandas as pd

def DataProcess():

    classList = pd.read_csv('Class.csv')

    classList = classList[['課綱', '停修與否', '課程代碼', '課程名稱', '課程類別', '開課班級', '必選修',
                           '學分', '授課教師', '時間1', '教室1', '時間2', '教室2', '時間3', '教室3', '備註']]

    classList = pd.DataFrame(classList)
    classList.fillna("無", inplace=True)


    return classList