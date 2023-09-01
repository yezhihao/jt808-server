#!/bin/sh

#jar文件名
JAR=jt808-server-1.0.0-SNAPSHOT.jar

#启动指定后缀的application.yml
ENV=test

CMD='java -jar '$JAR' --spring.profiles.active='$ENV
echo $CMD

pid=`ps -ef | grep "$CMD" | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ];then
    kill -15 $pid
    echo kill -15 $pid
    sleep 1
fi

pid=`ps -ef | grep "$CMD" | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ];then
    sleep 2
    kill -9 $pid
    echo kill -9 $pid
fi

if [ "$1" != "stop" ];then
    echo starting ...
    nohup $CMD > /dev/null 2>&1 &
    echo $!
    echo $! > /var/run/$JAR.$ENV.pid
    echo success
fi