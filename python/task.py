x = int(input('Введите горизонталь\n'))
y = int(input('Введите вертикаль\n'))

q = y
z = x - 1

while y > 0:
    print('*', end='')

    while z > 0:
        if y == q or y == 1:
            print('*', end='')
            z -= 1
        elif z != 1:
            print(' ', end='')
            z -= 1
        else:
            print('*', end='')
            z -= 1
    z = x - 1
    y -= 1
    print()
