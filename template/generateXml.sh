#!/bin/bash

##GENERATE


if [ $1 = "modifier"  ]
	then
	CONTENT="$(./template/modifierTemplate.sh $5)"
fi
./template/mainTemplate.sh $2 $3 $4 "$CONTENT"