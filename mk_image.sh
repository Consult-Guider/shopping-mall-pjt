#!/bin/bash

# 각 프로젝트 빌드.
source build.sh

# 설정
DIR_PJT="/home/park/바탕화면/shopping-mall-pjt"
DIR_SPRING=$DIR_PJT/back_end
DIR_VUE=$DIR_PJT/front_end

IMAGE_SPRING="iksadnorth/spring_app"
IMAGE_VUE="iksadnorth/vue_app"
IMAGE_VERSION="latest"

DELIMITER="========================================="

# spring build.
echo $DELIMITER
echo "Spring Image Build"
docker build -t $IMAGE_SPRING:$IMAGE_VERSION $DIR_SPRING

# vue build.
echo $DELIMITER
echo "Vue Image Build"
docker build -t $IMAGE_VUE:$IMAGE_VERSION $DIR_VUE

# Docker Login
echo $DELIMITER
echo "Docker Login"
docker Login

# Spring Image Push
echo $DELIMITER
echo "Spring Image Push"
docker push $IMAGE_SPRING:$IMAGE_VERSION

# Vue Image Push
echo $DELIMITER
echo "Vue Image Push"
docker push $IMAGE_VUE:$IMAGE_VERSION
