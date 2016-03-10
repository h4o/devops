#!/bin/bash
echo "[INFO] Running Tortues Ninja Framework"
PATH_POM="sample/pom.xml"

PROC_NAMES=( 'EmptyMutator' 'UnaryOperatorMutator' 'LogicalOperatorMutator' 'OverrideMethodRemovalMutator' )
declare -i cpt=0
echo "[INFO] Cleaning sample/output directory"
rm -r ./sample/output/processor/*
rm -r ./sample/output/tests/*

for NAME in ${PROC_NAMES[@]}
do

	PROC_PATH="com.mnt2.mutationFramework."${NAME}
	mvn test -f ${PATH_POM} -e -Dparam_processor=${PROC_PATH}
	# mv ./sample/target/mutationframework/* ./sample/output/processor/MUT-$((cpt)).xml
	mkdir ./sample/output/tests/mutant-$((cpt))
    mv ./sample/target/surefire-reports/TEST* ./sample/output/tests/mutant-$((cpt))/

    cpt=$((cpt + 1))
done

mvn clean compile -f "xmlAnalyzer/pom.xml" assembly:single
mv xmlAnalyzer/target/mnt2_xmlAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar .
java -jar mnt2_xmlAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar
