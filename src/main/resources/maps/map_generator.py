import random
import sys

sys.setrecursionlimit(10**9)

def random_enemy(probabilty):
    values = [True] + [False] * (probabilty - 1)
    return random.choice(values)
def func(num):
    enemy = num
    map_rows = []
    for i in range(num):
        row = []
        max_enemy_cnt = 2
        for j in range(num):
            if i == 0 and j == num - 1:
                row.append("$")
            elif i == (num // 2) and j == (num // 2):
                row.append("@")
            elif random_enemy(num) and enemy != 0 and max_enemy_cnt != 0:
                enemy -= 1
                row.append("#")
                max_enemy_cnt -= 1
            else:
                row.append(".")
        map_rows.append("".join(row))

    if (enemy != 0):
        func(num)
    else:
        map_str = "\n".join(map_rows)
        print(map_str)
        print("Enemies: ", num - enemy)


while True:
    try:
        print("Enter a number: ")
        num = int(input())
        func(num)
    except ValueError:
        print("Invalid input")
