list = []                 # создание пустого списка
# len(list)               - длина списка
# list.append()           - добавить в конец списка
# list.insert()           - добавить на определённый индекс
# list.remove()           - удалить по значению (только первое)
# list.index()            - узнать индекс по значению (только первый, если отсутствует - error)
# list.sort()             - сортирует список
# sorted(list)            - возвращает отсортированный список, не меняя оригинал
# list.reverse()          - перевернёт список в обратном порядке
# reversed(list)           - возвращает перевёрнутый список, не меняя оригинал

students = ['Ivan', 'Masha', 'Sasha']
students2 = ['Oleg', 'Alex']

if 'Ivan' in students:
    print('Yes')
if 'Ann' not in students:
    print('No')

print(len(students))         # 3
print(students[0])           # Ivan
print(students[-1])          # Sasha
print(students[:2])          # ['Ivan', 'Masha']
print(students[::-1])        # ['Sasha', 'Masha', 'Ivan']

print(students + students2)  # ['Ivan', 'Masha', 'Sasha', 'Oleg', 'Alex']

students2[1] = 'Alexei'
print(students2[1])          # Alexei

del students2[1]
print(students2)             # ['Oleg']

for s in students:
    print(s)

