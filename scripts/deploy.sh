#!/bin/bash

REPOSITORY=/home/ubuntu/atdd-subway-fare
PROJECT_NAME=atdd-subway-fare

echo "> ./gradlew clean build"
./gradlew clean build

echo "> Build 성공"

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl $PROJECT_NAME | grep java | awk '{print $1}')

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> 현재 구동중인 어플리케이션 pid: $CURRENT_PID"
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/build/libs/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar  \
    -Dspring.profiles.active=prod $JAR_NAME > ~/nohup.out 2>&1 &

echo "> cd ~"
cd ~
