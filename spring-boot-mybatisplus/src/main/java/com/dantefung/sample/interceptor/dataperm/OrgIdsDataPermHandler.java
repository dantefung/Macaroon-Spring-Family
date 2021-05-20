/*
 * Copyright (C), 2015-2020
 * FileName: EhrOrgIdsDataPermHandler
 * Author:   DANTE FUNG
 * Date:     2021/4/26 15:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/4/26 15:27   V1.0.0
 */
package com.dantefung.sample.interceptor.dataperm;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: EhrOrgIdsDataPermHandler
 * @Description: 本组织及以下
 * @author DANTE FUNG
 * @date 2021/04/26 15/27
 * @since JDK1.8
 */
@Slf4j
@Component
public class OrgIdsDataPermHandler implements DataPermInvocationHandler{

	private static final String EHR_ORG_IDS = "OrgIdsData";

	private static final String SQL_CLAUSE_UNION_SET_ORG = "and {0} in ( {1} )";

	@Override
	public boolean support(String funcPermCode) {
		return StringUtils.isNotBlank(funcPermCode) && funcPermCode.contains(EHR_ORG_IDS);
	}

	@Override
	public String handle(String funcPermCode) {
		log.info(">>>>>>>>>>>>>>> enter {} , start to handle...", this.getClass().getSimpleName());
		// 模拟RPC请求. 或 DB操作.
		List<String> ids = Lists.newArrayList("xxxx", "yyyy", "zzzz");
		String joinHandleResult = ids.stream().map(x -> String.format("'%s'", x)).collect(Collectors.joining(
				StrUtil.COMMA));
		String aliasCol = " R.org_id ";
		if (funcPermCode.contains("#")) {
			aliasCol = String.format(" %s ", funcPermCode.split("#")[1]);
		}
		return MessageFormat.format(SQL_CLAUSE_UNION_SET_ORG, aliasCol, joinHandleResult);
	}
}
