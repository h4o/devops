#!/bin/bash

PATH_POM="sample/pom.xml"

PROC_NAMES=( 'EmptyMutator' 'UnaryOperatorMutator' 'LogicalOperatorMutator' 'OverrideMethodRemovalMutator' )
declare -i cpt=0
echo "[INFO] Cleaning sample/output directory"
rm -r ./sample/output/processor/*
rm -r ./sample/output/tests/*

for NAME in ${PROC_NAMES[@]}
do
	while read -r conf
	do
		PROC_PATH="com.mnt2.mutationFramework."${NAME}
		if [[ ${NAME} = "LogicalOperatorMutator" || ${NAME} = "UnaryOperatorMutator" ]]
			then
			while read -r line
			do
			    
			    ./template/generateXml.sh modifier $conf "./sample/target/report/" "$line" > ./config/config.xml
				mvn test -f ${PATH_POM} -e -Dparam_processor=${PROC_PATH}
				mv ./sample/target/mutationframework/* ./sample/output/processor/MUT-$((cpt)).xml
			    mv ./sample/target/surefire-reports/TEST-* ./sample/output/tests/TEST-$((cpt)).xml

		    	cpt=$((cpt + 1))

			done < "./config/$NAME"
		else
			./template/generateXml.sh n $conf "./sample/target/report/" "$line" > ./config/config.xml
			
			mvn test -f ${PATH_POM} -e -Dparam_processor=${PROC_PATH}

			mv ./sample/target/mutationframework/* ./sample/output/processor/MUT-$((cpt)).xml
		    mv ./sample/target/surefire-reports/TEST-* ./sample/output/tests/TEST-$((cpt)).xml

		    cpt=$((cpt + 1))
		fi
	done < "./config/selector"
	rm .tmp

	
	
done

mvn clean compile -f "xmlAnalyzer/pom.xml" assembly:single
mv xmlAnalyzer/target/mnt2_xmlAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar .
java -jar mnt2_xmlAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar
