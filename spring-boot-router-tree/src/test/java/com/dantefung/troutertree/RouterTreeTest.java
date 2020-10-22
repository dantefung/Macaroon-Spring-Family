/*
 * Copyright (C), 2015-2018
 * FileName: RouterTreeTest
 * Author:   DANTE FUNG
 * Date:     2020/10/23 1:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/10/23 1:50   V1.0.0
 */
package com.dantefung.troutertree;

import com.dantefung.core.utils.JsonUtil;
import com.dantefung.troutertree.dto.AnnualCmdBody;
import com.dantefung.troutertree.dto.CmdHeader;
import com.dantefung.troutertree.dto.GenericCmdDto;
import com.dantefung.troutertree.service.RouterTreeService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title: RouterTreeTest
 * @Description:
 * @author DANTE FUNG
 * @date 2020/10/23 1:50
 */
public class RouterTreeTest extends Tester {

	@Autowired
	private RouterTreeService routerTreeService;

	@Test
	public void test() {
		GenericCmdDto<AnnualCmdBody> genericCmdDto = new GenericCmdDto<>();
		genericCmdDto.setCmd("ANN001");
		CmdHeader cmdHeader = new CmdHeader();
		cmdHeader.setVersion("1.0.0");
		genericCmdDto.setCmdHeader(cmdHeader);
		AnnualCmdBody annualCmdBody = new AnnualCmdBody();
		annualCmdBody.setEmployeeNoList(Lists.newArrayList("GT010575"));
		annualCmdBody.setStartDateStr("2020-10-22");
		annualCmdBody.setEndDateStr("2020-11-30");
		genericCmdDto.setCmdBody(annualCmdBody);
		System.out.println(JsonUtil.obj2Json(genericCmdDto));

		 routerTreeService.execute(JsonUtil.obj2Json(genericCmdDto));
	}
}
