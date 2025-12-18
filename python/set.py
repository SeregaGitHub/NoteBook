s = set()                               # создание пустого множества
basket = {'apple', 'orange', 'banana'}  # создание множества со значениями

for b in basket:
    print(b, end=' ')

print('orange' in basket)               # True

len(basket)                             # количество элементов во множестве
s.add('apple')                          # добавить
s.remove('orange')                      # удалить, если элемента нет - error
s.discard('banana')                     # удалить, если элемента нет - нет ошибки
s.clear()                               # удалить всё