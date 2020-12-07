/*
 * Copyright (C), 2015-2020
 * FileName: TokenInfo
 * Author:   DANTE FUNG
 * Date:     2020/12/8 12:55 上午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/8 12:55 上午   V1.0.0
 */
package com.dantefung.springbootmvc.resolver;

import lombok.Data;

import java.io.Serializable;

/**
 * @Title: TokenInfo
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/08 00/55
 * @since JDK1.8
 */
@Data
public class TokenInfo {

	String userId;

	String token;
}
