#!/bin/sh

#jar文件名
JAR=jt808-server-1.0.0-SNAPSHOT.jar

#启动指定后缀的application.yml
ENV=test

pid=`ps -ef | grep $JAR | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ];then
    kill -15 $pid
    echo kill -15 $pid
    sleep 1
fi

pid=`ps -ef | grep $JAR | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ];then
    sleep 2
    kill -9 $pid
    echo kill -9 $pid
fi

echo starting ...
nohup java -jar $JAR --spring.profiles.active=$ENV > /dev/null 2>&1 &
echo start success!
echo -e "\n"