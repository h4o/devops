#!/bin/bash

PATH_POM="sample/pom.xml"

PROC_NAMES=( 'UnaryOperatorMutator' 'LogicalOperatorMutator' )

for NAME in ${PROC_NAMES[@]}
do

	PROC_PATH="com.mnt2.mutationFramework."${NAME}

	mvn test -f ${PATH_POM} -e -Dparam_processor=${PROC_PATH}

done

mvn clean compile -f "xmlAnalyzer/pom.xml" assembly:single
mv xmlAnalyzer/target/mnt2_MutationFramework-1.0-SNAPSHOT-jar-with-dependencies.jar .
java -jar mnt2_MutationFramework-1.0-SNAPSHOT-jar-with-dependencies.jar
