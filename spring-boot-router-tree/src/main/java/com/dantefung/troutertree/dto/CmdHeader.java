/*
 * Copyright (C), 2015-2018
 * FileName: CmdHeader
 * Author:   DANTE FUNG
 * Date:     2020/10/22 20:23
 * Description: 指令头信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/10/22 20:23   V1.0.0
 */
package com.dantefung.troutertree.dto;

import lombok.Data;

/**
 * @Title: CmdHeader
 * @Description: 指令头信息
 * @author DANTE FUNG
 * @date 2020/10/22 20:23
 */
@Data
public class CmdHeader {

	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 跟踪ID
	 */
	private String traceId;

}
