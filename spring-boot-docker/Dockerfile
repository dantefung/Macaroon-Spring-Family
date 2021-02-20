## 注意: 这份文件是用来结合Dockerfile-maven-plugin使用的.
# Docker image for springboot file run
# VERSION 0.0.1
# Author: DANTE FUNG
# 基础镜像使用java
# FROM java:8
# Start with a base image containing Java runtime. The default timezone is Shanghai.
FROM registry.cn-shanghai.aliyuncs.com/shalousun/alpine-oraclejdk8-perf4j:1.0.2
# 作者
MAINTAINER DANTE FUNG <xxx@gmail.com>
# Make port 8080 available to the world outside this container
EXPOSE 8080
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp. Spring Boot使用的内嵌Tomcat容器默认使用/tmp作为工作目录。
VOLUME /tmp
# 将jar包添加到容器中并更名为app.jar
ADD target/spring-boot-dcoker-0.0.1-SNAPSHOT.jar app.jar
# Set jvm
ENV JAVA_OPTS="-server -Xmx512m -Xms512m -Djava.awt.headless=true"
# Spring boot profiles
ENV PROFILES="default"
# 运行jar包
RUN bash -c 'touch /app.jar'
# 表示容器复制完后执行的命令
# #/dev/urandom则是一个非阻塞的发生器
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=$PROFILES","/app.jar"]