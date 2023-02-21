#!/bin/bash

# 설정
DIR_NOW=`pwd`

DIR_PJT="/home/park/바탕화면/shopping-mall-pjt"
DIR_SPRING=$DIR_PJT/back_end
DIR_VUE=$DIR_PJT/front_end

JAVA_COMPILER="/usr/lib/jvm/java-17-openjdk-amd64"
OLD_JAVA_HOME=$JAVA_HOME
DELIMITER="========================================="

# spring build.
echo $DELIMITER
echo "spring 프로젝트로 이동."
cd $DIR_SPRING

echo $DELIMITER
echo "새롭게 정의한 JAVA_HOME"
export JAVA_HOME=$JAVA_COMPILER
echo $JAVA_HOME

echo $DELIMITER
echo "gradle build without test"
$DIR_SPRING/gradlew build -x test

echo $DELIMITER
echo "기존 방식으로 재정의한 JAVA_HOME"
export JAVA_HOME=$OLD_JAVA_HOME
echo $JAVA_HOME

# vue build.
echo $DELIMITER
echo "Vue 프로젝트로 이동."
cd $DIR_VUE

echo $DELIMITER
echo "npm run build"
npm run build

# 정리.
echo $DELIMITER
echo "경로 복귀."
cd $DIR_NOW
