/*
 * Copyright (C), 2015-2018
 * FileName: GenericCmdDto
 * Author:   DANTE FUNG
 * Date:     2020/10/22 20:19
 * Description: 年度排班调度器指令
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/10/22 20:19   V1.0.0
 */
package com.dantefung.troutertree.dto;

import lombok.Data;

/**
 * @Title: GenericCmdDto
 * @Description: 通用调度器指令
 * @author DANTE FUNG
 * @date 2020/10/22 20:19
 */
@Data
public class GenericCmdDto<T> {

	/**
	 * 指令码
	 */
	private String cmd;
	/**
	 * 指令头
	 */
	private CmdHeader cmdHeader;
	/**
	 * 指令体
	 */
	private T cmdBody;
}
