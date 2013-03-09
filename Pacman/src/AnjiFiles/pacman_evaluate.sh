#!/bin/bash
#echo off
export MYCLASSPATH=./properties
for i in `ls ./lib/*.jar`
do 
	export MYCLASSPATH=${MYCLASSPATH}:${i}
done
echo ${MYCLASSPATH}

nohup java -classpath ${MYCLASSPATH} -Xms256m -Xmx384m Anji.PacmanEvaluator $1 $2 &
