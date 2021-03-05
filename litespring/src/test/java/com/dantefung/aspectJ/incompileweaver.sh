#!/usr/bin/env bash
ASPECTJ_WEAVER=~/.m2/repository/org/aspectj/aspectjweaver/1.9.5/aspectjweaver-1.9.5.jar
ASPECTJ_RT=~/.m2/repository/org/aspectj/aspectjrt/1.9.5/aspectjrt-1.9.5.jar
ASPECTJ_TOOLS=~/.m2/repository/org/aspectj/aspectjtools/1.8.2/aspectjtools-1.8.2.jar

java -jar $ASPECTJ_TOOLS -cp $ASPECTJ_RT -source 1.8 -sourceroots . -d ../../../../../../target/classes