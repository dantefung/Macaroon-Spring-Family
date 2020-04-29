package com.dantefung.sample.report.controller;

import com.dantefung.sample.core.entity.SqlConfig;
import com.dantefung.sample.core.enums.ReportKeys;
import com.dantefung.sample.core.result.Result;
import com.dantefung.sample.core.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;


/**
 * 报表查询,统一入口
 */
@RestController
@Slf4j
public class CommonReportController {

	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "/getReport", method = RequestMethod.POST)
	public Object getReport(@RequestBody Map<String, Object> params) throws InvocationTargetException, IllegalAccessException {
		// Object data = ActionMappingUtil.invoke(requestParam.getKey(),requestParam);
		return Result.ok();
	}

	/**
	 * {
	 *   "key":"datactr:rpt:edf:stats"
	 * }
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/v2/getReport", method = RequestMethod.POST)
	public Object getReportv2(@RequestBody Map<String, Object> params){
		return Result.ok(commonService.selectList(params));
	}

	/**
	 * https://gitee.com/free/EntityMapper/blob/master/wiki/UseSqlMapper.md
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/v3/getReport", method = RequestMethod.POST)
	public Object getReportv3(@RequestBody Map<String, Object> params){
		// TODO SqlConfig应该是根据key从数据库查询出来的
		/*SqlConfig sqlConfig = new SqlConfig();
		sqlConfig.setSqlKey(requestParam.getKey());
		QueryWrapper qw = new QueryWrapper(sqlConfig);
		Assert.notNull(sqlConfig,"参数异常");*/
		SqlConfig sqlConfig = new SqlConfig();
		sqlConfig.setSqlKey(ReportKeys.RPT_ABC_REVENUE);
		sqlConfig.setSqlText("<script>"
				+ "        SELECT u.* FROM USER u\n" + "        <where>\n"
				+ "            <if test=\"id neq null and id neq ''\" >\n" + "                AND u.id = #{id}\n"
				+ "            </if>\n" + "        </where>\n" + "\n</script>");
		return commonService.selectCommonList(sqlConfig, params);
	}
	/**
	 * 为了让通用Mapper更彻底的支持多表操作以及更灵活的操作，
	 * 在<b>2.2.0版本</b>增加了一个可以直接执行SQL的新类SqlMapper。
	 * 注：3.3.0版本去掉了这个类，这个类现在在EntityMapper项目
	 */
}


