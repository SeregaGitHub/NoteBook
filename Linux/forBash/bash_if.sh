#! /bin/bash

read -p "Введите число" number

if [ 0 > "$number" ]; then
	echo "Некорректный ввод. Число меньше нуля."
	read -p "Введите число" number
elif [ 100 > "$number" ]; then
	String="Ваш выбор - $number"
	echo "$String"
elif [ "$number" == 100 ]; then
	echo "100 - ЛУЧШИЙ выбор !!!"
else
	echo "Ваш выбор - ФАТАЛЬНАЯ ошибка !!!"
	echo "Попыток больше НЕ будет !!!"
	echo "Выберите другой скрипт"
	ls
fi
