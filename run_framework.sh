#!/bin/bash

PATH_POM="sample/pom.xml"

PROC_NAMES=( 'UnaryOperatorMutator' 'LogicalOperatorMutator' )

for NAME in ${PROC_NAMES[@]}
do

	PROC_PATH="com.mnt2.mutationFramework."${NAME}

	mvn test -f ${PATH_POM} -Dparam_processor=${PROC_PATH}

done

