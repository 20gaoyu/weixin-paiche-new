#!/bin/sh
sh /apps/initDocker.sh
OPTS="$JAVA_OPTS -Dapp.id=cdn-log"
OPTS="$OPTS -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMPercentage=95.0"
java $OPTS -jar /soft/cdn-log-config-rest/cdn-log-config-rest.jar
