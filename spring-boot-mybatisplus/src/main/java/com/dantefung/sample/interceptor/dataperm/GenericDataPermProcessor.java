package com.dantefung.sample.interceptor.dataperm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单数据权限处理器
 * @author Benjamin
 */
@Component
@Slf4j
public class GenericDataPermProcessor implements DataPermProcessor {
    /**
     * 以下两个标识涉及用户中心、授权中心的缓存操作
     */

    @Autowired
	private ApplicationContext applicationContext;

    /**
     * 目前只支持一个sql中只有一个权限标识码
     *
     * @param funcPermList
     * @return
     */
    @Override
    public Map<String, String> process(List<String> funcPermList) {
        Map<String, String> sqlParsedMap = new HashMap<>();
        if (funcPermList == null || funcPermList.isEmpty()){
            return sqlParsedMap;
        }
		Map<String, DataPermInvocationHandler> dataPermInvocationHandlerMap = applicationContext
				.getBeansOfType(DataPermInvocationHandler.class);
		for (String funcPerm : funcPermList) {
			dataPermInvocationHandlerMap.values().forEach(handler -> {
				if (handler.support(funcPerm)) {
					sqlParsedMap.put(funcPerm, handler.handle(funcPerm));
				}
			});
		}
        return sqlParsedMap;
    }
}
