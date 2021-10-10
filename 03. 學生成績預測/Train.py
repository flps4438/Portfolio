import streamlit as st
import pandas as pd
from DataProcess import process_data
from sklearn.model_selection import train_test_split
from LinearTrain import linerTrain
from TreeTrain import treeTrain
from NNTrain import nnTrain

def train_page( choose, train_list, predict, old_data ):

    if choose == "回歸模型":

        linerTrain( choose, train_list, predict, old_data )

    elif choose == "決策樹":

        treeTrain( choose, train_list, predict, old_data )

    elif choose == "NN模型":

        nnTrain( choose, train_list, predict, old_data )