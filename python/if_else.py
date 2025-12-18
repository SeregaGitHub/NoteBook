x = int(input('Введите число\n'))

if (0 < x <= 2) or x == 12:
    print('Зима')
elif 2 < x <= 5:
    print('Весна')
elif 5 < x <= 8:
    print('Лето')
elif 8 < x <= 11:
    print('Осень')
else:
    print('No such season')