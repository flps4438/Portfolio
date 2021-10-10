# Copyright (c) Microsoft Corporation. All rights reserved.
# Licensed under the MIT License.

from botbuilder.core import ActivityHandler, MessageFactory, TurnContext
from botbuilder.schema import ChannelAccount


class EchoBot(ActivityHandler):
    async def on_members_added_activity(
        self, members_added: [ChannelAccount], turn_context: TurnContext
    ):
        for member in members_added:
            if member.id != turn_context.activity.recipient.id:
                await turn_context.send_activity("Hello and welcome!")

    async def on_message_activity(self, turn_context: TurnContext):

        text = turn_context.activity.text

        if text == "hi" or text == "Hi" or text == "嗨":

            return await turn_context.send_activity( MessageFactory.text(f"Hello! I'm robot"))

        elif text == "Bye" or text == "bye bye" or text == "掰掰":

            return await turn_context.send_activity( MessageFactory.text(f"Bye Bye~~~"))

        else:
            return await turn_context.send_activity( MessageFactory.text(f"Echo: {turn_context.activity.text}"))
