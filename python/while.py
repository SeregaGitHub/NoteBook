x = 5
while x > 0:
    if x % 2 == 1:
        print(x, end=' ')
    x -= 1

############################################

while True:
    x = int(input())
    if x < 10:
        continue
    if x > 100:
        break
    if 10 <= x <= 100:
        print(x)