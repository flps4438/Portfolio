# Copyright (c) Microsoft Corporation. All rights reserved.
# Licensed under the MIT License.

from pandas import DataFrame


class ConversationData:
    def __init__(
        self,
        id: str = None,
        timestamp: str = None,
        text: str = None,
        start: bool = False,
        level: int = -1,
        count: int = -1,
        classList = None,
        show: bool = False,
        select: bool = False,
        sport: bool = False,
        basic: bool = False,
        extend: bool = False,
        elective: bool = False,
        compulsory: bool = False,
        military: bool = False,
    ):
        self.id = id
        self.timestamp = timestamp
        self.text = text

        self.start = start

        self.level = level
        self.count = count
        self.classList = classList
        self.show = show
        self.select = select

        self.sport = sport
        self.basic = basic
        self.extend = extend
        self.elective = elective
        self.compulsory = compulsory
        self.military = military