# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/maven-plugin/)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#using-boot-devtools)

### Introduction
This project is used to test the automatic assembly of SpringBoot.
### Run it
```
mvn spring-boot:run
```
### Test it 
```
    curl http://127.0.0.1:8080/hello-world
```
### Console output
```
当前 WebServer 实现类为：org.springframework.boot.web.embedded.tomcat.TomcatWebServer
2020-01-27 14:59:00.856  INFO 3083 --- [  restartedMain] c.d.autoconf.AutoconfApplication         : Started AutoconfApplication in 16.603 seconds (JVM running for 17.199)
当前 helloWorld Bean 实现类为：org.springframework.web.reactive.function.server.RouterFunctions$DefaultRouterFunction
当前 WebConfiguration Bean 实现类为：com.dantefung.autoconf.config.WebConfiguration$$EnhancerBySpringCGLIB$$191247b6

```