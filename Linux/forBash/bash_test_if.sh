#! /bin/bash

read -p "Введите число" number

if [ "$number" -gt 101 ]; then
	echo "Некорректный ввод. Число больше 100."
else if [ "$number" -lt 0 ]; then
	echo "Число меньше нуля !!!"
else
	echo "Ваш выбор - Идеален"
fi
