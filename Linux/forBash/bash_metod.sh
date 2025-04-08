#! /bin/bash

myMetod() {
	echo "Params: $1 $2"
	if [ "$2" == 0 ]; then
		echo "Error"
	else
		result=$(($1/$2))
		echo "Res: $result"
	fi
}

myMetod 10 2

myMetod 10 0
