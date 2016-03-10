#!/bin/bash

NBBEFORE=$1

declare -a LISTAVANT
declare -a LISTAPRES
declare -i cpt=0


for param in "$@"
do
	if [ $cpt -ge 1 ]
		then
		if [ $cpt -le $NBBEFORE ]
			then
			LISTAVANT+=("$param")
		else
			LISTAPRES+=("$param")
		fi
	fi


	cpt=$((cpt + 1))
done

echo "<modifier>"
echo "<before>"
for avant in ${LISTAVANT[@]}
do
	echo "<value>$avant</value>"
done
echo "</before>"
echo "<after>"
for after in ${LISTAPRES[@]}
do
	echo "<value>$after</value>"
done
echo "</after>"

echo "</modifier>"