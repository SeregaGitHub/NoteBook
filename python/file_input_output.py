                                # Text from file

text_1 = open('some_file.txt')             # read file
text_2 = open('some_file.txt', 'r')        # read file

str1 = text_1.readline()                   # first line
str2 = text_1.readline()                   # second line

text_1.close()                             # after read file you need close stream


with open('some_file.txt', 'r') as inf:    # will close file automatically
    s1 = inf.readline()
    s2 = inf.readline()
    s3 = inf.readline().strip()            # strip() - уберёт из строки служебные символы: \n, \t и т.п.

with open('input.txt', 'r') as inf:        # read all lines in file
    for line in inf:
        line = line.strip()
        print(line)

                                # Text in file

ouf = open('output.txt', 'w')              # открыть файл для записи
ouf.write('some_text\n')                   # записать текст - some_text
x = 25
ouf.write(str(x))                          # чтобы записать число - его нужно принудительно преобразовать в строку
ouf.close()                                # after wright file you need close stream

with open('some_output.txt', 'w') as out:  # will close file automatically
    out.write('some_text\n')
    ouf.write(str(x))