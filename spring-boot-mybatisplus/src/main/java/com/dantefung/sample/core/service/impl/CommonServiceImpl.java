package com.dantefung.sample.core.service.impl;

import com.dantefung.sample.core.entity.SqlConfig;
import com.dantefung.sample.core.enums.ReportKeyEnums;
import com.dantefung.sample.core.service.CommonService;
import com.dantefung.sample.core.sql.SqlMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {

	private static final String KEY = "key";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private SqlMapper sqlMapper;

    @Override
    public List<Map<String, Object>> selectCacheableList(Map<String,Object> params) {
        return sqlSessionTemplate.selectList(getNameSpaceSqlId(params.get(KEY).toString()),params);
    }

    @Override
    public List<Map<String, Object>> selectList(Map<String,Object> params) {
        return sqlSessionTemplate.selectList(getNameSpaceSqlId(params.get(KEY).toString()),params);
    }

    @Override
    public List<Map<String, Object>> selectCommonCacheableList(SqlConfig sqlConfig, Map<String,Object> params) {
        return selectCommonList(sqlConfig,params);
    }

    @Override
    public List<Map<String, Object>> selectCommonList(SqlConfig sqlConfig, Map<String,Object> params) {
        return sqlMapper.selectList(sqlConfig.getSqlText(), params);
    }

    private String getNameSpaceSqlId(String key){
        return ReportKeyEnums.getEnum(key).getSqlKey();
    }
}
