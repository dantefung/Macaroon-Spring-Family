/*
 * Copyright (C), 2015-2018
 * FileName: MailPropertiesA
 * Author:   DANTE FUNG
 * Date:     2020/7/1 16:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/7/1 16:02   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Title: MailPropertiesA
 * @Description:
 * 这种用法最简单，直接在POJO类上加上注解即可，Spring容器初始化时就会生成配置类实例了。适合POJO类是自定义的。
 *
 * @author DANTE FUNG
 * @date 2020/7/1 16:02
 */
@Data
@ToString
@Component
@ConfigurationProperties(prefix = "kaka.cream.mail-a",ignoreUnknownFields = false)
public class MailPropertiesA {

	private String name;
	private String sex;
	private Integer age;
}
