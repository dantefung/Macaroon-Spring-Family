/*
 * Copyright (C), 2015-2018
 * FileName: AnnualCmdBody
 * Author:   DANTE FUNG
 * Date:     2020/10/22 20:30
 * Description: 年度排班指令体
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/10/22 20:30   V1.0.0
 */
package com.dantefung.troutertree.dto;

import lombok.Data;

import java.util.List;

/**
 * @Title: AnnualCmdBody
 * @Description: 年度排班指令体
 * @author DANTE FUNG
 * @date 2020/10/22 20:30
 */
@Data
public class AnnualCmdBody {

	/**
	 * 员工编号列表
	 */
	private List<String> employeeNoList;
	/**
	 * 生成排班开始时间
	 */
	private String startDateStr;
	/**
	 * 生成排班结束时间
	 */
	private String endDateStr;
}
