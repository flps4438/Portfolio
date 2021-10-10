#!/usr/bin/env python3
# Copyright (c) Microsoft Corporation. All rights reserved.
# Licensed under the MIT License.

import os

""" Bot Configuration """


class DefaultConfig:
    """ Bot Configuration """

    PORT = 3978
    APP_ID = os.environ.get("MicrosoftAppId", "")
    APP_PASSWORD = os.environ.get("MicrosoftAppPassword", "")

    # (需修改) 此ID為創建新LUIS APP的所使用的辨識LUIS APP ID
    #LUIS_APP_ID = os.environ.get("LuisAppId", "9e689047-0c73-4eb0-83f6-dd7c066c1340")
    LUIS_APP_ID = os.environ.get("LuisAppId", "6d3ff93b-c484-4647-8c1d-1abf7274eb3b")
    # (需修改) 此KEY為Azure Portal上LUIS Service的KEY
    #LUIS_API_KEY = os.environ.get("LuisAPIKey", "28af010173e6454ea494be81e0aa40c0")
    LUIS_API_KEY = os.environ.get("LuisAPIKey", "93a17b38334c42f59c05b01415c66ee1")
    # (可修改) 依照LUIS Service創建地區而變更
    LUIS_API_HOST_NAME = os.environ.get("LuisAPIHostName", "westus.api.cognitive.microsoft.com")
    # (可修改) 創建luis服務時，需要用到的變數
    LUIS_APP_VERSION_ID = '0.1'
