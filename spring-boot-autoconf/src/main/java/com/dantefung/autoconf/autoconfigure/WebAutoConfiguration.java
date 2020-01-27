/**
 * Copyright (C), 2018-2020, 独立开发者DanteFung
 * FileName: WebAutoConfiguration
 * Author:   admin
 * Date:     2020-01-27 14:45
 * Description: 自动装配类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dantefung.autoconf.autoconfigure;

import com.dantefung.autoconf.config.WebConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Dante Fung

 * @create 2020-01-27 14:45

 * @desc 自动装配类

 * @since 1.0.0
 **/
@ConditionalOnWebApplication
@Configuration
@Import(WebConfiguration.class)
public class WebAutoConfiguration {
}
