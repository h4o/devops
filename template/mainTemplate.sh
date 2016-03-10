#!/bin/sh


TYPE=$1
VALUE=$2
DIR=$3


MODIFIERS=$4


echo "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><modifiers type=\"$TYPE\" value=\"$VALUE\" output=\"$DIR\">$MODIFIERS</modifiers>"