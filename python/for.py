# range(start, to, step)

for i in range(5):       # 0 1 2 3 4
    print(i, end=' ')

print()

for i in range(2, 5):    # 2 3 4
    print(i, end=' ')

print()

for i in range(2, 10, 2): # 2 4 6 8
    print(i, end=' ')

# for e in list:         получение элементов списка
#     print(e)

############################################################################

# 	     5	   6
# 7	    35    42
# 8	    40	  48
# 9	    45	  54
# 10	50	  60

a = int(input())
b = int(input())
c = int(input())
d = int(input())
if (a <= 10 and b <= 10 and c <= 10 and d <= 10) and (a <= b and c <= d):
    for i in range(c, d + 1):
        print('\t', i, end='')
    print()
    for u in range(a, b + 1):
        print(u, end='\t')
        for z in range(c, d + 1):
            print(z * u, end='\t')
        print()