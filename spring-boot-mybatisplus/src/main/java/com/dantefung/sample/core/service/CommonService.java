package com.dantefung.sample.core.service;


import com.dantefung.sample.core.entity.SqlConfig;

import java.util.List;
import java.util.Map;

public interface CommonService {

    List<Map<String,Object>> selectCacheableList(Map<String,Object> params);

    List<Map<String,Object>> selectList(Map<String,Object> params);

    List<Map<String, Object>> selectCommonCacheableList(SqlConfig sqlConfig, Map<String,Object> params);

    List<Map<String, Object>> selectCommonList(SqlConfig sqlConfig, Map<String,Object> params);
}
