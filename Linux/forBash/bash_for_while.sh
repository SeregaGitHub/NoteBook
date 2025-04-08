#! /bin/bash

for (( i=1; i<=3; i++ )); do
	echo "El: $i"
done

for i in {1..3}; do
	echo "El: $i"
done

i=1
while [ "$i" -lt 4 ]; do
	echo "El: $i"
	i=$(("$i"+1))
done
