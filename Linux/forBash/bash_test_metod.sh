#! /bin/bash

myMetod() {
	read -p "Введите первое число" first
	read -p "Введите второе число" second
	if [ "$second" == 0 ]; then
		echo "Error"
	else
		result=$(($first/$second))
		echo "Res: $result"
	fi
}

myMetod 
