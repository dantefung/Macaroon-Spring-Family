# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/maven-plugin/)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#using-boot-devtools)


### Spring Boot Actuator
#### **监控端点endpoints介绍**
ID	|描述|
:-:|:-:|
auditevents |	公开当前应用程序的审核事件信息。|
beans	|显示应用程序中所有Spring bean的完整列表。|
caches	|暴露可用的缓存。
conditions	|显示在配置和自动配置类上评估的条件以及它们匹配或不匹配的原因。|
configprops	|显示所有的整理列表@ConfigurationProperties,查看配置属性，包括默认配置|
env	|露出Spring的属性的各种环境变量,后面可跟/{name}查看具体的值|
flyway	|显示已应用的任何Flyway数据库迁移。|
health	|显示应用健康信息,在spring boot2.0以后需要在配置里show-details打开所有健康信息|
httptrace	|显示HTTP跟踪信息（默认情况下，最后100个HTTP请求 - 响应交换）,2.0以后需要手动打开|
info	|显示任意应用信息,是在配置文件里自己定义的|
integrationgraph	|显示Spring Integration图。|
loggers	|显示和修改应用程序中记录器的配置。|
liquibase	|显示已应用的任何Liquibase数据库迁移。|
metrics	|显示当前应用程序的“指标”信息,比如内存用量和HTTP请求计数,后可跟/{name}查看具体值|
mappings	|显示所有@RequestMapping路径的整理列表。|
scheduledtasks	|显示应用程序中的计划任务。|
sessions	|允许从Spring Session支持的会话存储中检索和删除用户会话。使用Spring Session对响应式Web应用程序的支持时不可用|
shutdown	|允许应用程序正常关闭。|
threaddump	|执行线程转储。|

如果您的应用程序是Web应用程序（Spring MVC，Spring WebFlux或Jersey），则可以使用以下附加端点：
ID	|描述|
:-:|:-:|
heapdump	|返回hprof堆转储文件。|
jolokia	|通过HTTP公开JMX bean（当Jolokia在类路径上时，不适用于WebFlux）。|
logfile	|返回日志文件的内容（如果已设置logging.file或logging.path属性）。支持使用HTTP Range标头检索部分日志文件的内容。|
prometheus	|以可以由Prometheus服务器抓取的格式公开指标。|

### application.yml
```
management:
  endpoints:
    web:
      exposure:
        include: "*"
```



