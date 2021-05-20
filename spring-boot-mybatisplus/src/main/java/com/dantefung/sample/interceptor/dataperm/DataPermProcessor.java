package com.dantefung.sample.interceptor.dataperm;

import java.util.List;
import java.util.Map;

/**
 * 数据权限处理器接口
 *
 * @author Tim Deng
 */
public interface DataPermProcessor {

    Map<String,String> process(List<String> dataPermList);

}
