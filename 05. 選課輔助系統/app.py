# # Copyright (c) Microsoft Corporation. All rights reserved.
# # Licensed under the MIT License.
#
# import sys
# import traceback
# from datetime import datetime
# from http import HTTPStatus
#
# from aiohttp import web
# from aiohttp.web import Request, Response, json_response
# from botbuilder.core import (
#     BotFrameworkAdapterSettings,
#     TurnContext,
#     BotFrameworkAdapter,
# )
# from botbuilder.core.integration import aiohttp_error_middleware
# from botbuilder.schema import Activity, ActivityTypes
#
# from bots import EchoBot
# from bots.luis_bot import LuisBot
# from config import DefaultConfig
#
# CONFIG = DefaultConfig()
#
# # Create adapter.
# # See https://aka.ms/about-bot-adapter to learn more about how bots work.
# SETTINGS = BotFrameworkAdapterSettings(CONFIG.APP_ID, CONFIG.APP_PASSWORD)
# ADAPTER = BotFrameworkAdapter(SETTINGS)
#
#
# # Catch-all for errors.
# async def on_error(context: TurnContext, error: Exception):
#     # This check writes out errors to console log .vs. app insights.
#     # NOTE: In production environment, you should consider logging this to Azure
#     #       application insights.
#     print(f"\n [on_turn_error] unhandled error: {error}", file=sys.stderr)
#     traceback.print_exc()
#
#     # Send a message to the user
#     await context.send_activity("The bot encountered an error or bug.")
#     await context.send_activity(
#         "To continue to run this bot, please fix the bot source code."
#     )
#     # Send a trace activity if we're talking to the Bot Framework Emulator
#     if context.activity.channel_id == "emulator":
#         # Create a trace activity that contains the error object
#         trace_activity = Activity(
#             label="TurnError",
#             name="on_turn_error Trace",
#             timestamp=datetime.utcnow(),
#             type=ActivityTypes.trace,
#             value=f"{error}",
#             value_type="https://www.botframework.com/schemas/error",
#         )
#         # Send a trace activity, which will be displayed in Bot Framework Emulator
#         await context.send_activity(trace_activity)
#
#
# ADAPTER.on_turn_error = on_error
#
# # Create the Bot
# BOT = EchoBot()
# BOT = LuisBot(CONFIG, CONVERSATION_STATE, USER_STATE, DIALOG)
#
#
# # Listen for incoming requests on /api/messages
# async def messages(req: Request) -> Response:
#     # Main bot message handler.
#     if "application/json" in req.headers["Content-Type"]:
#         body = await req.json()
#     else:
#         return Response(status=HTTPStatus.UNSUPPORTED_MEDIA_TYPE)
#
#     activity = Activity().deserialize(body)
#     auth_header = req.headers["Authorization"] if "Authorization" in req.headers else ""
#
#     response = await ADAPTER.process_activity(activity, auth_header, BOT.on_turn)
#     if response:
#         return json_response(data=response.body, status=response.status)
#     return Response(status=HTTPStatus.OK)
#
#
# APP = web.Application(middlewares=[aiohttp_error_middleware])
# APP.router.add_post("/api/messages", messages)
#
# if __name__ == "__main__":
#     try:
#         web.run_app(APP, host="localhost", port=CONFIG.PORT)
#     except Exception as error:
#         raise error

from aiohttp import web
from aiohttp.web import Request, Response, json_response
from botbuilder.core import (
    BotFrameworkAdapterSettings,
    TurnContext,
    BotFrameworkAdapter,
    ConversationState,
    MemoryStorage,
    UserState
)
from botbuilder.core.integration import aiohttp_error_middleware
from botbuilder.azure import BlobStorage, BlobStorageSettings
from botbuilder.schema import Activity, ActivityTypes

from bots.luis_bot import LuisBot
from config import DefaultConfig

import sys
import traceback
from datetime import datetime

CONFIG = DefaultConfig()
# Create adapter.
# See https://aka.ms/about-bot-adapter to learn more about how bots work.
SETTINGS = BotFrameworkAdapterSettings(CONFIG.APP_ID, CONFIG.APP_PASSWORD)
ADAPTER = BotFrameworkAdapter(SETTINGS)


# Catch-all for errors.
async def on_error(context: TurnContext, error: Exception):
    # This check writes out errors to console log .vs. app insights.
    # NOTE: In production environment, you should consider logging this to Azure
    #       application insights.
    print(f"\n [on_turn_error] unhandled error: {error}", file=sys.stderr)
    traceback.print_exc()

    # Send a message to the user
    await context.send_activity("The bot encountered an error or bug.")
    await context.send_activity(
        "To continue to run this bot, please fix the bot source code."
    )
    # Send a trace activity if we're talking to the Bot Framework Emulator
    if context.activity.channel_id == "emulator":
        # Create a trace activity that contains the error object
        trace_activity = Activity(
            label="TurnError",
            name="on_turn_error Trace",
            timestamp=datetime.utcnow(),
            type=ActivityTypes.trace,
            value=f"{error}",
            value_type="https://www.botframework.com/schemas/error",
        )
        # Send a trace activity, which will be displayed in Bot Framework Emulator
        await context.send_activity(trace_activity)
    # Clear out state
    await CONVERSATION_STATE.delete(context)

ADAPTER.on_turn_error = on_error

BLOB_SETTINGS = BlobStorageSettings(
    container_name="flps4436",
    account_name="flps4436",
    connection_string="DefaultEndpointsProtocol=https;AccountName=flps4436;AccountKey=LVZlIDIfyMjuzBcKkHDS/K4BVkLlI1BGbLbhz5sd4smIRWHHmj3Wy4K5il2CITTBUkmMyT4HF0rZKS67+quQ6Q==;EndpointSuffix=core.windows.net"
    )
MEMORY = BlobStorage(BLOB_SETTINGS)

# Create MemoryStorage, UserState and ConversationState
# MEMORY = MemoryStorage()
CONVERSATION_STATE = ConversationState(MEMORY)
USER_STATE = UserState(MEMORY)

# create main dialog and bot
BOT = LuisBot(CONFIG, CONVERSATION_STATE, USER_STATE)


# Listen for incoming requests on /api/messages
async def messages(req: Request) -> Response:
    # Main bot message handler.
    if "application/json" in req.headers["Content-Type"]:
        body = await req.json()
    else:
        return Response(status=415)

    activity = Activity().deserialize(body)
    auth_header = req.headers["Authorization"] if "Authorization" in req.headers else ""

    response = await ADAPTER.process_activity(activity, auth_header, BOT.on_turn)
    if response:
        return json_response(data=response.body, status=response.status)
    return Response(status=201)


APP = web.Application(middlewares=[aiohttp_error_middleware])
APP.router.add_post("/api/messages", messages)

if __name__ == "__main__":
    try:
        web.run_app(APP, host="localhost", port=CONFIG.PORT)
    except Exception as error:
        raise error

