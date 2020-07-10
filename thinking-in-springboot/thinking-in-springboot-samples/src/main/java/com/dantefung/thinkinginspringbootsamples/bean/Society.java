/*
 * Copyright (C), 2015-2018
 * FileName: Society
 * Author:   DANTE FUNG
 * Date:     2020/7/10 17:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/7/10 17:02   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.bean;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @Title: Society
 * @Description:
 * @author DANTE FUNG
 * @date 2020/7/10 17:02
 */
@Data
public class Society {

	private String name;

	private List<SecurityUser> members = Lists.newArrayList();
}
