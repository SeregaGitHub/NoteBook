str1 = 'some_string'
str2 = "some_string"

print(str2.split('_')) # ['some', 'string']

str3 = '''some big
          string'''
str4 = """some big
          string"""

str5 = 'abc' + 'def'   # abcdef
str6 = 'abc' * 2       # abcabc
print(len(str5))       # 6

print('===\n')
print('===', '\n')
print('===', end=' ') # === ===
print('===', '\n')

for i in range(0, 4):       # s o m e 
    print(str1[i], end=' ')