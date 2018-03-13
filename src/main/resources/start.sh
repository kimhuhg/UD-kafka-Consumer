#!/bin/bash

source /etc/profile

nohup java -jar \
-Dfile.encoding=utf-8 \
-server \
-Djava.awt.headless=true \
-Xms4144m \
-Xmx4144m \
-XX:NewSize=1024m \
-XX:MaxNewSize=2048m \
-XX:PermSize=512m \
-XX:MaxPermSize=512m \
-XX:MaxTenuringThreshold=15 \
-XX:NewRatio=2 \
-XX:+AggressiveOpts \
-XX:+UseBiasedLocking \
-XX:+UseConcMarkSweepGC \
-XX:+UseParNewGC \
-XX:+CMSParallelRemarkEnabled \
-XX:LargePageSizeInBytes=128m \
-XX:+UseFastAccessorMethods \
-XX:+UseCMSInitiatingOccupancyOnly \
-XX:+DisableExplicitGC \
/home/resources/com.udgrp-1.0-SNAPSHOT.jar > /home/resources/nohup.out &

echo "The ConsumerStartApplication is started!"