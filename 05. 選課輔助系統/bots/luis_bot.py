import time
from datetime import datetime

from botbuilder.ai.luis import LuisApplication, LuisRecognizer, LuisPredictionOptions
from botbuilder.core import ActivityHandler, TurnContext, RecognizerResult, ConversationState, UserState
from botbuilder.schema import ChannelAccount
from DataProcess import DataProcess

from config import DefaultConfig
from data_models import ConversationData

import pandas as pd

class LuisBot(ActivityHandler):
    def __init__( self, config: DefaultConfig, conversation_state: ConversationState, user_state: UserState):

        luis_application = LuisApplication(
            config.LUIS_APP_ID, 
            config.LUIS_API_KEY, 
            "https://" + config.LUIS_API_HOST_NAME
        )

        luis_options = LuisPredictionOptions(include_all_intents=True, include_instance_data=True)
        self.recognizer = LuisRecognizer(luis_application, luis_options, True)

        self.subscription_key = config.LUIS_API_KEY
        self.version_id = config.LUIS_APP_VERSION_ID
        # 可空白或直接填寫一個現有的luis app，不過需要注意其version_id是否與config.py裡的一樣
        self.luis_appid = ''

        if conversation_state is None:
            raise TypeError("[DialogBot]: Missing parameter. conversation_state is required but None was given")
        if user_state is None:
            raise TypeError("[DialogBot]: Missing parameter. user_state is required but None was given")
        if conversation_state is None:
            raise TypeError("[StateManagementBot]: Missing parameter. conversation_state is required but None was given")
        if user_state is None:
            raise TypeError("[StateManagementBot]: Missing parameter. user_state is required but None was given")

        self.conversation_state = conversation_state
        self.user_state = user_state
        self.classList = DataProcess()
        self.conversation_data_accessor = self.conversation_state.create_property("ConversationData")

    async def on_turn(self, turn_context: TurnContext):

        await super().on_turn(turn_context)
        await self.conversation_state.save_changes(turn_context)
        await self.user_state.save_changes(turn_context)

    # 首次加入的問候的訊息
    async def on_members_added_activity(self, members_added: [ChannelAccount], turn_context: TurnContext):

        for member in members_added:

            if member.id != turn_context.activity.recipient.id:

                await turn_context.send_activity(f"歡迎來到自動創建LUIS APP的機器人，請說出你的需求")

    start = False

    level = -1
    count = -1
    classList = DataProcess()
    show = False
    select = False

    sport = False
    basic = False
    extend = False
    elective = False
    compulsory = False
    military = False

    # 每一次對話都是由這裏來處理
    async def on_message_activity(self, turn_context: TurnContext):

        conversation_data = await self.conversation_data_accessor.get(turn_context, ConversationData)
        print(conversation_data.text)

        self.readData(conversation_data)

        # 辨識使用者傳的訊息
        recognizer_result = await self.recognizer.recognize(turn_context)

        # 找出最高可能的意圖
        intent = LuisRecognizer.top_intent(recognizer_result)

        print( recognizer_result.text + " : " + intent)

        # 針對每個意圖做出不同的動作
        if self.sport and ( intent != "找時間" and intent != "找教室" and intent != "找老師" and intent != "肯定" and intent != "否定"):

            if not self.start:

                self.reset()

            self.count, self.classList = await self.findSportClass(recognizer_result)

            if self.count > 0:

                self.show = False
                self.start = True
                await turn_context.send_activity(f"總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")

            else:

                await turn_context.send_activity(f"抱歉好像沒有您要的結果欸")
                self.reset()

        elif self.basic and ( intent != "找時間" and intent != "找教室" and intent != "找老師" and intent != "肯定" and intent != "否定"):

            if not self.start:

                self.reset()

            self.count, self.classList = await self.findBasicKnowledge(recognizer_result)

            if self.count > 0:

                self.start = True
                self.show = False
                await turn_context.send_activity(f"總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")

            else:

                await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")
                self.reset()

        elif self.extend and ( intent != "找時間" and intent != "找教室" and intent != "找老師" and intent != "肯定" and intent != "否定"):

            if not self.start:

                self.reset()

            self.count, self.classList = await self.findExtendKnowledge(recognizer_result)

            if self.count > 0:

                self.start = True
                self.show = False
                await turn_context.send_activity(f"總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")

            else:

                await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")
                self.reset()

        elif self.elective and ( intent != "找時間" and intent != "找教室" and intent != "找老師" and intent != "肯定" and intent != "否定"):

            if not self.start:

                self.reset()

            self.count, self.classList = await self.findElective(recognizer_result)

            if self.count > 0:

                self.start = True
                self.show = False
                await turn_context.send_activity(f"總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")

            else:

                await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")
                self.reset()

        elif self.compulsory and ( intent != "找時間" and intent != "找教室" and intent != "找老師" and intent != "肯定" and intent != "否定"):

            if not self.start:

                self.reset()

            self.count, self.classList = await self.findCompulsory(recognizer_result)

            if self.count > 0:

                self.start = True
                self.show = False
                await turn_context.send_activity(f"總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")

            else:

                await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")
                self.reset()

        elif self.military and (intent != "找時間" and intent != "找教室" and intent != "找老師" and intent != "肯定" and intent != "否定"):

            if not self.start:
                self.reset()

            self.count, self.classList = await self.findMilitary(recognizer_result)

            if self.count > 0:

                self.start = True
                self.show = False
                await turn_context.send_activity(f"總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")

            else:

                await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")
                self.reset()

        elif intent == "體育":

            self.reset()
            classList = self.classList
            self.classList = classList[classList['課程類別'] == "體育"]

            if recognizer_result.text.__contains__("體育") and not self.sport:

                await turn_context.send_activity(f"想修什麼體育課呢?(時間/地點/老師/課程名稱/一般體育/網球/桌球/....)")

                self.sport = True

            else:

                self.count, self.classList = await self.findSportClass(recognizer_result)

                if self.count > 0 :

                    self.start = True
                    self.show = False
                    await turn_context.send_activity(f"總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")

                else:

                    await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")
                    self.reset()

        elif intent == "找基礎通識":

            self.reset()
            classList = self.classList
            self.classList = classList[classList['開課班級'] == "基礎必修通識"]

            if ( recognizer_result.text.__contains__("基礎") or recognizer_result.text.__contains__("通識") )  and not self.basic:

                await turn_context.send_activity(f"想修什麼基礎通識課呢?(時間/地點/老師/課程名稱/宗哲/人哲....)")

                self.basic = True

            else :

                self.count, self.classList = await self.findBasicKnowledge(recognizer_result)

                if self.count > 0:

                    self.start = True
                    self.show = False
                    await turn_context.send_activity(f"總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")

                else:

                    await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")
                    self.reset()

        elif intent == "找延伸通識":

            self.reset()
            classList = self.classList
            self.classList = classList[classList['開課班級'] == "延伸選修通識"]

            if ( recognizer_result.text.__contains__("延伸") or recognizer_result.text.__contains__("通識") )  and not self.basic:

                await turn_context.send_activity(f"想修什麼延伸通識課呢?(時間/地點/老師/課程名稱....)")

                self.extend = True

            else :

                self.count, self.classList = await self.findExtendKnowledge(recognizer_result)

                if self.count > 0:

                    self.start = True
                    self.show = False
                    await turn_context.send_activity(f"總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")

                else:

                    await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")
                    self.reset()

        elif intent == "找資工選修":

            self.reset()
            classList = self.classList
            self.classList = classList[(classList['開課班級'].str.contains("資訊")) & (classList['必選修'] == "選修")]

            if (recognizer_result.text.__contains__("系選") or recognizer_result.text.__contains__("選修") )  and not self.elective:

                await turn_context.send_activity(f"想修什麼系選呢?")
                self.elective = True

            else :

                self.count, self.classList = await self.findElective(recognizer_result)

                if self.count > 0:

                    self.start = True
                    self.show = False
                    await turn_context.send_activity(f"總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")

                else:

                    await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")
                    self.reset()

        elif intent == "找資工必修":

            self.reset()
            classList = self.classList
            self.classList = classList[(classList['開課班級'].str.contains("資訊")) & (classList['必選修'] == "必修")]

            if recognizer_result.text.__contains__("必修") and not self.compulsory:

                await turn_context.send_activity(f"想修什麼必修呢?")
                self.compulsory = True

            else :

                self.count, self.classList = await self.findCompulsory(recognizer_result)

                if self.count > 0:

                    self.start = True
                    self.show = False
                    await turn_context.send_activity(f"總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")

                else:

                    await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")
                    self.reset()

        elif intent == "找軍訓":

            self.reset()
            classList = self.classList
            self.classList = classList[classList['課程類別'].str.contains("軍訓")]

            if ( recognizer_result.text.__contains__("軍訓") or recognizer_result.text.__contains__("國防") ) and not self.military:

                await turn_context.send_activity(f"想修什麼軍訓呢?")
                self.military = True

            else :

                self.count, self.classList = await self.findMilitary(recognizer_result)

                if self.count > 0:

                    self.start = True
                    print("AAAA")
                    self.show = False
                    await turn_context.send_activity(f"總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")

                else:

                    await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")
                    self.reset()


        elif intent == "肯定":

            print("肯定：" + str(self.level))

            if self.start and not self.show and self.level != -1:

                await self.creatAnsList(turn_context)

                if self.level < 3:

                    await turn_context.send_activity(f"要篩選其他條件嗎?")
                    self.show = True

                elif self.level == 3:
                    await turn_context.send_activity(f"篩選條件已達到3個上限，要看最終篩選出的結果嗎?")
                    self.show = False
                    self.level += 1

                else :
                    await turn_context.send_activity(f"感謝您的使用~")
                    await turn_context.send_activity(f"有想要查課的話歡迎再次呼叫我~")
                    self.reset()

                self.select = False

            elif self.start and not self.select:

                await turn_context.send_activity(f"想要篩選什麼呢?")
                self.select = True

            else: await turn_context.send_activity(f"肯定的回覆")

        elif intent == "否定":

            if self.start and not self.show and self.level < 3:

                await turn_context.send_activity(f"要不要加一點篩選條件?比如說選時間或是老師之類的?")
                self.show = True
                self.select = False

            elif self.start and not self.select: # reset all

                self.reset()
                await turn_context.send_activity(f"好的，祝您有個美好的一天~")
                await turn_context.send_activity(f"有想要查課的話歡迎呼叫我~")

            else:
                await turn_context.send_activity(f"否定的回覆")

        elif intent == "找老師":

            old_classList = self.classList.copy()

            if self.start:

                self.level += 1

            else:

                self.start = True
                self.level = 1

            self.count, teacher = await self.selectTeacher(recognizer_result)

            if self.count > 0:

                await turn_context.send_activity(f"{teacher}\n\n總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")
                self.show = False

            else:

                self.classList = old_classList
                self.level -= 1
                await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")

        elif intent == "找時間":

            old_classList = self.classList.copy()

            if self.start:

                self.level += 1

            else:

                self.start = True
                self.level = 1

            self.count, time = await self.selectTime(recognizer_result)

            if self.count > 0:

                await turn_context.send_activity(f"{time}\n\n總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")
                self.show = False

            else:

                self.classList = old_classList
                self.level -= 1
                await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")

        elif intent == "找教室":

            old_classList = self.classList.copy()

            if self.start:

                self.level += 1

            else:

                self.start = True
                self.level = 1

            self.count, place = await self.selectPlace(recognizer_result)

            if self.count > 0:

                await turn_context.send_activity(f"{place}\n\n總共有 {self.count} 個結果\n\n要幫您顯示出來嗎?")
                self.show = False

            else:

                self.classList = old_classList
                self.level -= 1
                await turn_context.send_activity(f"抱歉好像沒有您要的結果欸，要不要換個試試看")

        elif intent == "打招呼":

            await turn_context.send_activity(f"嗨您好，我是選課小幫手，今天過得好嗎?")

        elif intent == "掰掰語":

            await turn_context.send_activity(f"掰掰掰掰掰，下次再見")

        elif intent == "找課":

            await turn_context.send_activity(f"嗨嗨嗨，要找什麼課挖")

        elif recognizer_result.text == "重置":

            self.reset()
            await turn_context.send_activity("所有參數已重置")

        else :

            await turn_context.send_activity(f"我聽不懂您在說什麼欸，要不要換種說法試試看呢?說不定我會聽懂哦")

        await self.writeData(turn_context, recognizer_result, conversation_data)

    async def findMilitary(self, recognizer_result:RecognizerResult):

        classList = self.classList
        sportClassList = classList[classList['課程類別'].str.contains("軍訓")]

        self.level += 1

        try:

            try:

                wantClass = recognizer_result.entities.get('軍訓課程')[0][0]

            except TypeError:

                wantClass = recognizer_result.text

            compulsoryList = sportClassList[sportClassList['課程名稱'].str.contains(wantClass, regex = False)]

            print("WantSport=" + wantClass + "/END")
            print(compulsoryList)

            return len(compulsoryList), compulsoryList

        except TypeError:

            return 0, "找不到結果"


    async def findCompulsory(self, recognizer_result:RecognizerResult):

        classList = self.classList
        sportClassList = classList[(classList['開課班級'].str.contains("資訊")) & (classList['必選修'] == "必修")]

        self.level += 1

        try:

            try:

                wantClass = recognizer_result.entities.get('資工必修課程')[0][0]

            except TypeError:

                wantClass = recognizer_result.text

            compulsoryList = sportClassList[sportClassList['課程名稱'].str.contains(wantClass, regex = False)]

            print("WantSport=" + wantClass + "/END")
            print(compulsoryList)

            return len(compulsoryList), compulsoryList

        except TypeError:

            return 0, "找不到結果"


    async def findElective(self, recognizer_result:RecognizerResult):

        classList = self.classList
        sportClassList = classList[(classList['開課班級'].str.contains("資訊")) & (classList['必選修'] == "選修")]

        self.level += 1

        try:

            try:

                wantClass = recognizer_result.entities.get('資工選修課程')[0][0]

            except TypeError:

                wantClass = recognizer_result.text

            extendList = sportClassList[sportClassList['課程名稱'].str.contains(wantClass)]

            print("WantSport=" + wantClass + "/END")

            return len(extendList), extendList

        except TypeError:

            return 0, "找不到結果"


    async def findExtendKnowledge(self, recognizer_result:RecognizerResult):

        classList = self.classList
        sportClassList = classList[classList['開課班級'] == "延伸選修通識"]

        self.level += 1

        try:

            try:

                wantClass = recognizer_result.entities.get('延伸通識')[0][0]
                extendList = sportClassList[sportClassList['課程名稱'].str.contains(wantClass)]

            except TypeError:

                wantClass = recognizer_result.entities.get('延伸類別')[0][0]
                extendList = sportClassList[sportClassList['課程類別'].str.contains(wantClass)]

            print("WantSport=" + wantClass + "/END")

            return len(extendList), extendList

        except TypeError:

            return 0, "找不到結果"

    async def findBasicKnowledge(self, recognizer_result:RecognizerResult):

        classList = self.classList
        sportClassList = classList[classList['開課班級'] == "基礎必修通識"]

        self.level += 1

        try:

            wantSport = recognizer_result.entities.get('基礎通識')[0][0]

            print("WantSport=" + wantSport + "/END")

            basicList = sportClassList[sportClassList['課程名稱'].str.contains(wantSport)]

            return len(basicList), basicList

        except TypeError:

            return 0, "找不到結果"

    async def findSportClass(self, recognizer_result:RecognizerResult):

        classList = self.classList
        sportClassList = classList[classList['課程類別'] == "體育"]
        self.level += 1

        try:

            wantSport = recognizer_result.entities.get('體育課')[0][0]

            print("WantSport=" + wantSport + "/END")

            sportList = sportClassList[sportClassList['課程名稱'].str.contains(wantSport)]

            return len(sportList), sportList

        except TypeError:

            return 0, "找不到結果"

    def reset(self):

        self.start = False
        self.count = -1
        self.level = -1
        self.show = False
        self.select = False
        self.classList = DataProcess()

        self.sport = False
        self.basic = False
        self.extend = False
        self.elective = False
        self.compulsory = False
        self.military = False


    async def creatAnsList(self, turn_context:TurnContext):


        frameList = self.classList

        print(frameList)

        showList = ""
        count = 0


        for index, list in frameList.iterrows():

            count += 1
            showList += list['課程代碼'] + "\t" + list['課程名稱'] + "\t" + list["時間1"]

            if list["時間2"] != "無" :

                showList += (' / ' + list["時間2"])

            if list["時間3"] != "無":

                showList += (' / ' + list["時間3"])

            showList = showList + "\t" + list["授課教師"] + "\n\n"

            if count == 40:

                await turn_context.send_activity(f"{showList}")

                count = 0
                showList = ""

        if showList != "":

            await turn_context.send_activity(f"{showList}")

    async def selectTeacher(self, recognizer_result:RecognizerResult):

        nowList = self.classList

        print(nowList)

        try:

            wantTeacher = recognizer_result.entities.get('老師')[0][0]
            wantTeacher = str(wantTeacher).replace(' ', '')

            print("Teacher=" + wantTeacher + "/END")

            ansList = nowList[nowList['授課教師'].str.contains(wantTeacher)]

            print(ansList)

            self.classList = ansList

            return len(ansList), recognizer_result.text

        except TypeError:

            return 0, recognizer_result.text

    async def selectTime(self, recognizer_result:RecognizerResult):

        nowList = self.classList

        print(nowList)

        try:

            category = str(recognizer_result.entities.get('時間類別')[0][0]).replace(' ', '')
            wantTime = str(recognizer_result.entities.get('時間')[0][0]).replace(' ', '')


            print("Category=" + category + "/END")
            print("Time=" + wantTime + "/END")

            ansList = nowList[nowList[category].str.contains(wantTime)]

            print(ansList)

            self.classList = ansList

            return len(ansList), recognizer_result.text

        except TypeError:

            return 0, recognizer_result.text

    async def selectPlace(self, recognizer_result:RecognizerResult):

        nowList = self.classList

        print(nowList)

        try:
            category = str(recognizer_result.entities.get('教室類別')[0][0]).replace(' ', '')
            wantPlace = str(recognizer_result.entities.get('教室')[0][0]).replace(' ', '')

            print("Place=" + wantPlace + "/END")

            ansList = nowList[nowList[category].str.contains(wantPlace)]

            self.classList = ansList

            return len(ansList), recognizer_result.text

        except TypeError:

            return 0, recognizer_result.text


    async def writeData(self, turn_context:TurnContext, recognizer_result:RecognizerResult, conversation_data:ConversationData):

        conversation_data.id = turn_context.activity.recipient.id
        conversation_data.timestamp = self.__datetime_from_utc_to_local(turn_context.activity.timestamp)
        conversation_data.text = recognizer_result.text

        conversation_data.start = self.start

        conversation_data.level = self.level
        conversation_data.count = self.count
        conversation_data.classList = self.classList.values
        conversation_data.show = self.show
        conversation_data.select = self.select

        conversation_data.sport = self.sport
        conversation_data.basic = self.basic
        conversation_data.extend = self.extend
        conversation_data.elective = self.elective
        conversation_data.compulsory = self.compulsory
        conversation_data.military = self.military

        return conversation_data

    def readData(self, conversation_data:ConversationData ):

        self.start = conversation_data.start

        self.level = conversation_data.level
        self.count = conversation_data.count
        self.classList = pd.DataFrame(conversation_data.classList,
                                      columns=['課綱', '停修與否', '課程代碼', '課程名稱', '課程類別', '開課班級', '必選修',
                                               '學分', '授課教師', '時間1', '教室1', '時間2', '教室2', '時間3', '教室3', '備註'])
        self.show = conversation_data.show
        self.select = conversation_data.select

        self.sport = conversation_data.sport
        self.basic = conversation_data.basic
        self.extend = conversation_data.extend
        self.elective = conversation_data.elective
        self.compulsory = conversation_data.compulsory
        self.military = conversation_data.military


    def __datetime_from_utc_to_local(self, utc_datetime):
        now_timestamp = time.time()
        offset = datetime.fromtimestamp(now_timestamp) - datetime.utcfromtimestamp(
            now_timestamp
        )
        result = utc_datetime + offset
        return result.strftime("%I:%M:%S %p, %A, %B %d of %Y")
