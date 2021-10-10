# 演算法分析機測
# 學號 : 10727242
# 姓名 : 林威成
# 中原大學資訊工程系

import re
from operator import itemgetter, attrgetter


class node():
    index = 0
    frequency = 0
    left = None
    right = None
    position = 0
    code = ""

    def __init__(self, position, index, frequency):
        self.position = position
        self.index = index
        self.frequency = frequency

    def __repr__(self):
        return repr((self.index, self.frequency))

    def setLeft(self, left):
        self.left = left

    def setRight(self, right):
        self.right = right

    def IsMerge(self):
        return self.left == None and self.right == None


def ReadObject():
    intputStr = input()

    slipt = re.split(r'[ \t]', intputStr)

    while '' in slipt:
        slipt.remove('')

    index = slipt[0]
    frequency = int(slipt[1])

    return index, frequency


def decode(lastlist: list, node: node, code):
    if code != "-1":
        node.code = str(code)

    if node.left is not None:
        decode(lastlist, node.left, node.code + "0")

    if node.right is not None:
        decode(lastlist, node.right, node.code + "1")

    if node.index != '':
        lastlist.append([node.position, node.index, node.code])


count = 0
n = int(input())

objectList = []

while n != 0:

    objectList = []
    count += 1

    for i in range(0, n):
        index, frequency = ReadObject()

        o = node(i, index, frequency)

        objectList.append(o)

    objectList = sorted(objectList, key=attrgetter('frequency'))

    while len(objectList) > 1:
        o1 = objectList.pop(0)
        o2 = objectList.pop(0)

        newObject = node(-1, '', o1.frequency + o2.frequency)
        newObject.left = o1
        newObject.right = o2

        objectList.append(newObject)

        objectList = sorted(objectList, key=attrgetter('frequency'))

    lastList = []
    decode(lastList, objectList[0], "-1")

    lastList = sorted(lastList, key=itemgetter(0))

    print("Huffman Codes #", count)

    for n in lastList:

        print(str(n[1]) + " " + str(n[2]))

    n = int(input())