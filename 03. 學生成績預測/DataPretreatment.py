import pandas as pd
import streamlit as st

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

def pretreatment():

    data = pd.read_csv("student-por.csv")

    data = pd.DataFrame(data)

    replace(data)

    return data
