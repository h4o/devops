#!/bin/bash

PATH_POM="sample/pom.xml"

PROC_NAMES=( 'EmptyProcessor' 'UnaryOperatorMutator' 'LogicalOperatorMutator' 'OverrideMethodRemovalProcessor' )
declare -i cpt=0
for NAME in ${PROC_NAMES[@]}
do

	PROC_PATH="com.mnt2.mutationFramework."${NAME}
	mvn test -f ${PATH_POM} -Dparam_processor=${PROC_PATH}
	mv ./sample/target/mutationframework/com.mnt2.mutationFramework.* ./sample/output/processor/MUT-$((cpt)).xml
    mv ./sample/target/surefire-reports/TEST-* ./sample/output/tests/TEST-$((cpt)).xml
    cpt=$((cpt + 1))
done

mvn clean compile -f "xmlAnalyzer/pom.xml" assembly:single
mv xmlAnalyzer/target/mnt2_xmlAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar .
java -jar mnt2_xmlAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar
