import pandas as pd
from sklearn.model_selection import train_test_split
from DataPretreatment import pretreatment

def process_data(predict):

    data = pretreatment()
    data = pd.DataFrame(data)


    if predict == "G1":

        data_all = data[['school', 'sex', 'age', 'address', 'Medu', 'Fedu', 'traveltime',
                         'studytime', 'failures', 'higher', 'internet', 'freetime',
                         'Dalc', 'Walc', 'absences']]
        ans = data['G1']

    elif predict == "G2":

        data_all = data[['school', 'sex', 'age', 'address', 'Medu', 'Fedu', 'traveltime',
                         'studytime', 'failures', 'higher', 'internet', 'freetime',
                         'Dalc', 'Walc', 'absences', 'G1']]
        ans = data['G2']

    else:  # predict == "G3":

        data_all = data[['school', 'sex', 'age', 'address', 'Medu', 'Fedu', 'traveltime',
                         'studytime', 'failures', 'higher', 'internet', 'freetime',
                         'Dalc', 'Walc', 'absences', 'G1', 'G2']]
        ans = data['G3']

    data_train, data_test, ans_train, ans_test = train_test_split(data_all, ans, test_size=0.3)

    return data_train, ans_train, data_test, ans_test
