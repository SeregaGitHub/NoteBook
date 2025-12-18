emp_dict = {}
empty_dict = dict()

d = {
    'a': 239,
    10: 100
}
print(d['a'])        # 239
print(d[10])         # 100
print(d)             # {'a': 239, 10: 100}

print('a' in d)      # True
print('a' not in d)  # False
d['b'] = 999
print(d)             # {'a': 239, 10: 100, 'b': 999}
# print(d['z'])        z - no in dict -> error
print(d.get('z'))    # None
del d['b']           # delete b from dict

for key in d.keys():
    print(key, end=' ')

for val in d.values():
    print(val, end=' ')

for key, val in d.items():
    print(key, val, end='; ')  # a 239; 10 100;